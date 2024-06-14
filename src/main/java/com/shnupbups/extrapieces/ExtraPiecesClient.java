package com.shnupbups.extrapieces;

import com.shnupbups.extrapieces.register.ModModels;
import com.shnupbups.extrapieces.register.ModRenderLayers;
import net.fabricmc.api.ClientModInitializer;

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

		ModRenderLayers.init();
		ModModels.init(ExtraPieces.PACK);
	}
}
