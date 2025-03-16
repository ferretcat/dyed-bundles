package archives.tater.bundlebackportish.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static archives.tater.bundlebackportish.BundleBackportishItems.*;

public class LangGenerator extends FabricLanguageProvider {

    public LangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(WHITE_BUNDLE, "White Bundle");
        translationBuilder.add(ORANGE_BUNDLE, "Orange Bundle");
        translationBuilder.add(MAGENTA_BUNDLE, "Magenta Bundle");
        translationBuilder.add(LIGHT_BLUE_BUNDLE, "Light Blue Bundle");
        translationBuilder.add(YELLOW_BUNDLE, "Yellow Bundle");
        translationBuilder.add(LIME_BUNDLE, "Lime Bundle");
        translationBuilder.add(PINK_BUNDLE, "Pink Bundle");
        translationBuilder.add(GRAY_BUNDLE, "Gray Bundle");
        translationBuilder.add(LIGHT_GRAY_BUNDLE, "Light Gray Bundle");
        translationBuilder.add(CYAN_BUNDLE, "Cyan Bundle");
        translationBuilder.add(PURPLE_BUNDLE, "Purple Bundle");
        translationBuilder.add(BLUE_BUNDLE, "Blue Bundle");
        translationBuilder.add(BROWN_BUNDLE, "Brown Bundle");
        translationBuilder.add(GREEN_BUNDLE, "Green Bundle");
        translationBuilder.add(RED_BUNDLE, "Red Bundle");
        translationBuilder.add(BLACK_BUNDLE, "Black Bundle");
    }
}
