package archives.tater.bundlebackportish.datagen;

import archives.tater.bundlebackportish.BundleBackportish;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;

import java.util.Optional;

import static archives.tater.bundlebackportish.BundleBackportishItems.*;

public class ModelGenerator extends FabricModelProvider {
    public ModelGenerator(FabricDataOutput output) {
        super(output);
    }

    private static void registerBundle(Item bundleItem, ItemModelGenerator itemModelGenerator) {
        var filledModelId = ModelIds.getItemSubModelId(bundleItem, "_filled");

        var filledOverride = new JsonObject();
        var predicate = new JsonObject();
        predicate.addProperty(BundleBackportish.id("filled").toString(), 0.0000001);
        filledOverride.add("predicate", predicate);
        filledOverride.addProperty("model", filledModelId.toString());
        var overrides = new JsonArray();
        overrides.add(filledOverride);

        // { "predicate": { "filled": 0.0000001 }, "model": "item/bundle_filled" }
        var baseModel = Models.GENERATED.upload(ModelIds.getItemModelId(bundleItem), TextureMap.layer0(bundleItem), itemModelGenerator.writer, (id, textures) -> {
            var json = Models.GENERATED.createJson(id, textures);
            json.add("overrides", overrides);
            return json;
        });
        new Model(Optional.of(baseModel), Optional.empty(), TextureKey.LAYER0).upload(filledModelId, TextureMap.layer0(TextureMap.getSubId(bundleItem, "_filled")), itemModelGenerator.writer);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        registerBundle(WHITE_BUNDLE, itemModelGenerator);
        registerBundle(ORANGE_BUNDLE, itemModelGenerator);
        registerBundle(MAGENTA_BUNDLE, itemModelGenerator);
        registerBundle(LIGHT_BLUE_BUNDLE, itemModelGenerator);
        registerBundle(YELLOW_BUNDLE, itemModelGenerator);
        registerBundle(LIME_BUNDLE, itemModelGenerator);
        registerBundle(PINK_BUNDLE, itemModelGenerator);
        registerBundle(GRAY_BUNDLE, itemModelGenerator);
        registerBundle(LIGHT_GRAY_BUNDLE, itemModelGenerator);
        registerBundle(CYAN_BUNDLE, itemModelGenerator);
        registerBundle(PURPLE_BUNDLE, itemModelGenerator);
        registerBundle(BLUE_BUNDLE, itemModelGenerator);
        registerBundle(BROWN_BUNDLE, itemModelGenerator);
        registerBundle(GREEN_BUNDLE, itemModelGenerator);
        registerBundle(RED_BUNDLE, itemModelGenerator);
        registerBundle(BLACK_BUNDLE, itemModelGenerator);
    }
}
