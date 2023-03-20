package gregtech.common.tileentities.machines.multiblock;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.enums.Textures.BlockIcons.*;
import static gregtech.api.multitileentity.multiblock.base.MultiBlockPart.*;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.tuple.Pair;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizon.structurelib.util.Vec3Impl;

import gregtech.api.enums.TierEU;
import gregtech.api.interfaces.ITexture;
import gregtech.api.multitileentity.multiblock.base.MultiBlock_Stackable;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;

public class MultiBlock_Macerator extends MultiBlock_Stackable<MultiBlock_Macerator> {

    private IStructureDefinition<MultiBlock_Macerator> STRUCTURE_DEFINITION = null;

    @Override
    public String getTileEntityName() {
        return "gt.multitileentity.multiblock.macerator";
    }

    @Override
    public IStructureDefinition<MultiBlock_Macerator> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<MultiBlock_Macerator>builder()
                    .addShape(
                            STACKABLE_TOP,
                            transpose(new String[][] { { " CCC ", "CCCCC", "CCCCC", "CCCCC", " CCC " }, }))
                    .addShape(
                            STACKABLE_MIDDLE,
                            transpose(new String[][] { { "  BBB  ", " B---B ", "DC---CD", " B---B ", "  BBB  " }, }))
                    .addShape(
                            STACKABLE_BOTTOM,
                            transpose(new String[][] { { " A~F ", "AAAAA", "AAAAA", "AAAAA", " AAA " }, }))
                    .addElement('A', ofChain(addMultiTileCasing(getCasingRegistryID(), getCasingMeta(), ENERGY_IN)))
                    .addElement(
                            'B',
                            ofChain(
                                    addMultiTileCasing(
                                            getCasingRegistryID(),
                                            getCasingMeta(),
                                            FLUID_IN | ITEM_IN | FLUID_OUT | ITEM_OUT)))
                    .addElement('C', addMultiTileCasing(getCasingRegistryID(), getCasingMeta(), NOTHING))
                    .addElement('D', addMultiTileCasing(getCasingRegistryID(), getCasingMeta(), NOTHING))
                    .addElement(
                            'F',
                            ofChain(
                                    addMultiTileCasing(getCasingRegistryID(), 20001, NOTHING),
                                    addMultiTileCasing(getCasingRegistryID(), 20002, NOTHING)))
                    .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public short getCasingRegistryID() {
        return getMultiTileEntityRegistryID();
    }

    @Override
    public short getCasingMeta() {
        return 18000;
    }

    @Override
    public boolean hasTop() {
        return true;
    }

    @Override
    protected GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Macerator").addInfo("Controller for the Macerator").addSeparator()
                .beginVariableStructureBlock(7, 9, 2 + getMinStacks(), 2 + getMaxStacks(), 7, 9, true)
                .addController("Bottom Front Center").addCasingInfoExactly("Test Casing", 60, false)
                .addEnergyHatch("Any bottom layer casing")
                .addInputHatch("Any non-optional external facing casing on the stacks")
                .addInputBus("Any non-optional external facing casing on the stacks")
                .addOutputHatch("Any non-optional external facing casing on the stacks")
                .addOutputBus("Any non-optional external facing casing on the stacks")
                .addStructureInfo(
                        String.format("Stackable middle stacks between %d-%d time(s).", getMinStacks(), getMaxStacks()))
                .toolTipFinisher("Wildcard");
        return tt;
    }

    @Override
    public int getMinStacks() {
        return 1;
    }

    @Override
    public int getMaxStacks() {
        return 10;
    }

    @Override
    public Vec3Impl getStartingStructureOffset() {
        return new Vec3Impl(2, 0, 0);
    }

    @Override
    public Vec3Impl getStartingStackOffset() {
        return new Vec3Impl(1, 1, 0);
    }

    @Override
    public Vec3Impl getPerStackOffset() {
        return new Vec3Impl(0, 1, 0);
    }

    @Override
    public Vec3Impl getAfterLastStackOffset() {
        return new Vec3Impl(-1, 0, 0);
    }

    @Override
    public ITexture[] getTexture(Block aBlock, byte aSide, boolean isActive, int aRenderPass) {
        // TODO: MTE(Texture)
        if (mFacing == aSide) {
            return new ITexture[] {
                    // Base Texture
                    MACHINE_CASINGS[1][0],
                    // Active
                    isActive()
                            ? TextureFactory.builder().addIcon(OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_ACTIVE).extFacing()
                                    .build()
                            : TextureFactory.builder().addIcon(OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE).extFacing()
                                    .build(),
                    // Active Glow
                    isActive()
                            ? TextureFactory.builder().addIcon(OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_ACTIVE_GLOW)
                                    .extFacing().glow().build()
                            : TextureFactory.builder().addIcon(OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_GLOW).extFacing()
                                    .glow().build() };
        }
        // Base Texture
        return new ITexture[] { MACHINE_CASINGS[1][0] };
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        if (isSeparateInputs()) {
            for (Pair<ItemStack[], String> tItemInputs : getItemInputsForEachInventory()) {
                if (processRecipe(aStack, tItemInputs.getLeft(), tItemInputs.getRight())) {
                    return true;
                }
            }
            return false;
        } else {
            ItemStack[] tItemInputs = getInventoriesForInput().getStacks().toArray(new ItemStack[0]);
            return processRecipe(aStack, tItemInputs, null);
        }
    }

    private boolean processRecipe(ItemStack aStack, ItemStack[] aItemInputs, String aInventory) {
        GT_Recipe_Map tRecipeMap = GT_Recipe_Map.sMaceratorRecipes;
        GT_Recipe tRecipe = tRecipeMap.findRecipe(this, false, TierEU.IV, null, aItemInputs);
        if (tRecipe == null) {
            return false;
        }

        if (!tRecipe.isRecipeInputEqual(true, false, 1, null, aItemInputs)) {
            return false;
        }

        mMaxProgressTime = tRecipe.mDuration;

        setItemOutputs(tRecipe.mOutputs, aInventory);
        return true;
    }
}
