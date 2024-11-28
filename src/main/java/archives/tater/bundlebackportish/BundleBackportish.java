package archives.tater.bundlebackportish;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BundleBackportish implements ModInitializer {
	public static final String MOD_ID = "bundle-backportish";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final RecipeSerializer<BundleColoringRecipe> COLORING_RECIPE = Registry.register(
			Registries.RECIPE_SERIALIZER,
			new Identifier(MOD_ID, "crafting_special_bundlecoloring"),
			new SpecialRecipeSerializer<>(BundleColoringRecipe::new)
	);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		BundleBackportishItems.register();

		BundleBackportishNetworking.register();

        //noinspection OptionalGetWithoutIsPresent
        ResourceManagerHelper.registerBuiltinResourcePack(
				new Identifier(MOD_ID, "rabbithide"),
				FabricLoader.getInstance().getModContainer(MOD_ID).get(),
				Text.of("Rabbit Hide Bundle"),
				ResourcePackActivationType.NORMAL
		);
	}
}
