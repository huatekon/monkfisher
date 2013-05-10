package org.huatekon.monkfisher;

import java.awt.Graphics;

import org.huatekon.monkfisher.nodes.Deposit;
import org.huatekon.monkfisher.nodes.Fish;
import org.huatekon.monkfisher.utilities.Paint;
import org.huatekon.monkfisher.utilities.Variables;
import org.huatekon.utilities.Strategies;
import org.huatekon.utilities.Strategy;
import org.huatekon.utilities.Utilities;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.util.Random;

@Manifest(authors = { "huatekon" }, name = "MonkFisher", version = 1.0, description = "Fish monks at Psicatoris")
public class MonkFisher extends ActiveScript implements MessageListener,
		PaintListener {

	public void onStart() {
		Strategies.add(new Deposit(), new Fish());
	}

	public void onStop() {
		System.out.println(Variables.monksCatched);
	}

	@Override
	public int loop() {
		if (Game.getClientState() == Game.INDEX_MAP_LOADED) {
			for (Strategy strategy : Strategies.get()) {
				if (strategy.validate()) {
					strategy.execute();
					return Random.nextInt(250, 350);
				}
			}
		}
		return 1000;
	}

	@Override
	public void messageReceived(MessageEvent m) {
		if (m.getMessage().equals("You catch a monkfish.")){
			Variables.monksCatched++;
		}
		if(m.getMessage().equals("Some anagogic orts fall to the floor.")){
			System.out.println("Getting orts");
			Utilities.getAnagogic();
		}

	}

	@Override
	public void onRepaint(Graphics g) {
		Paint.onRepaint(g);
	}

}
