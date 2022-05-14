package routorio;

import routorio.content.*;

import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.mod.*;

public class Routorio extends Mod {
	public Routorio() {
		Log.warn("Loading Routorio 3 - EXPECT CRASHES");
	}

	@Override
	public void loadContent() {
		RItems.load();
		RLiquids.load();
		RBlocks.load();
	}
}
