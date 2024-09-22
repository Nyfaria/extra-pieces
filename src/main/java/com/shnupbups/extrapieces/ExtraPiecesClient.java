package com.shnupbups.extrapieces;

import com.shnupbups.extrapieces.register.ModModels;
import com.shnupbups.extrapieces.register.ModRenderLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;

public class ExtraPiecesClient implements ClientModInitializer {
//todo: fix this if its broken
	public void onInitializeClient() {
		//try {
//		ArtificeResourcePackImpl assets = new ArtificeResourcePackImpl(ResourceType.CLIENT_RESOURCES, null, assets2 -> {
//			ModModels.init((RuntimeResourcePack) assets2);
//			assets2.setDescription("Assets necessary for Extra Pieces.");
//		});
//		Artifice.registerAssets(ExtraPieces.getID("ep_assets"), assets)
		/*.dumpResources(FabricLoader.getInstance().getConfigDirectory().getParent()+"/dump")*/;
		/*} catch(Exception e) {
			ExtraPieces.log("BIG OOF: "+e.getMessage());
		}*/
		ClientLoginConnectionEvents.INIT.register((client,a) -> {
//			ModModels.init();
			ModRenderLayers.init();
		});
	}
}
