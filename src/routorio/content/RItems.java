package routorio.content;

import arc.graphics.*;
import mindustry.type.*;

public class RItems {
	public static Item routerium, neutronRouter;

	public static void load() {
		routerium = new Item("routerium", Color.valueOf("6e7080")) {{
			cost = 3f;
			healthScaling = 1.5f;
		}};

		neutronRouter = new Item("neutron-router", Color.valueOf("d1e5ed")) {{
			explosiveness = 1000f;
			buildable = false;
		}};
	}
}
