package gregtech.loaders.postload.recipes;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;

public class PackagerRecipes implements Runnable {

    @Override
    public void run() {
        GT_Values.RA.addBoxingRecipe(
                ItemList.IC2_Scrap.get(9L),
                ItemList.Schematic_3by3.get(0L),
                ItemList.IC2_Scrapbox.get(1L),
                16,
                1);
    }
}
