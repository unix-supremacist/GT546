package gregtech.loaders.postload.recipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

public class SmelterRecipes implements Runnable {

    @Override
    public void run() {
        GT_ModHandler.addSmeltingRecipe(new ItemStack(Items.slime_ball, 1), ItemList.IC2_Resin.get(1L));

        GT_ModHandler.addSmeltingRecipe(
                GT_OreDictUnificator.get(OrePrefixes.ore, Materials.Graphite, 1L),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 1L));
        GT_ModHandler.addSmeltingRecipe(
                GT_OreDictUnificator.get(OrePrefixes.oreBlackgranite, Materials.Graphite, 1L),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 1L));
        GT_ModHandler.addSmeltingRecipe(
                GT_OreDictUnificator.get(OrePrefixes.oreEndstone, Materials.Graphite, 1L),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 1L));
        GT_ModHandler.addSmeltingRecipe(
                GT_OreDictUnificator.get(OrePrefixes.oreNetherrack, Materials.Graphite, 1L),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 1L));
        GT_ModHandler.addSmeltingRecipe(
                GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Iron, 1L),
                GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.WroughtIron, 1L));
        GT_ModHandler.addSmeltingRecipe(
                GT_OreDictUnificator.get(OrePrefixes.oreRedgranite, Materials.Graphite, 1L),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 1L));
    }
}
