package gregtech.loaders.oreprocessing;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

public class ProcessingFood implements gregtech.api.interfaces.IOreRecipeRegistrator {

    public ProcessingFood() {
        OrePrefixes.food.add(this);
    }

    @Override
    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName,
            ItemStack aStack) {
        switch (aOreDictName) {
            case "foodCheese":
                GT_OreDictUnificator.addItemData(aStack, new gregtech.api.objects.ItemData(Materials.Cheese, 3628800L));
                break;
            case "foodDough":
                GT_ModHandler.removeFurnaceSmelting(aStack);
        }
    }
}
