package routorio.content;

import arc.graphics.*;
import mindustry.type.*;

public class RLiquids {
	public static Liquid liquidRouter, fusionMix;

	public static void load() {
		liquidRouter = new Liquid("liquid-router", Color.valueOf("6e7080")) {{
			heatCapacity = 0.1f;
			temperature = 1.1f;
			viscosity = 1f;
		}};

		fusionMix = new Liquid("fusion-mix", Color.valueOf("390e04")) {{
			heatCapacity = 0.1f;
			temperature = 3.0f;
			viscosity = 0.2f;
		}};
	}
}
