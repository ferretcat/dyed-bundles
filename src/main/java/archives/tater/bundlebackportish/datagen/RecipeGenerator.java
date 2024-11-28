package archives.tater.bundlebackportish.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class RecipeGenerator extends FabricRecipeProvider {

    public RecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        new ShapedRecipeJsonBuilder(RecipeCategory.TOOLS, Items.BUNDLE, 1)
                .pattern("%")
                .pattern("#")
                .input('%', Items.STRING)
                .input('#', Items.LEATHER)
                .criterion(hasItem(Items.STRING), conditionsFromItem(Items.STRING))
                .offerTo(consumer);
    }
}
