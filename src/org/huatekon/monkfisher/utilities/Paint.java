package org.huatekon.monkfisher.utilities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;

public class Paint {

	private static AlphaComposite makeComposite(float alpha) {
		int type = AlphaComposite.SRC_OVER;

		return (AlphaComposite.getInstance(type, alpha));

	}
	
	public static void onRepaint(Graphics g){
		
		if(Variables.timer == null)
			Variables.timer = new Timer(0);
		
		if(Variables.startxp == 0)
			Variables.startxp = Skills.getExperience(Skills.FISHING);
		
		Graphics2D g2d = (Graphics2D) g;
		Rectangle bg = new Rectangle(300, 395, 220, 128);
		g2d.setComposite(makeComposite(.5f));
		g2d.setColor(Color.BLACK);
		g2d.fill(bg);
		g.setColor(Color.GREEN);
		
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.drawString(" MonkFisher v1.0 by huatekon ", 320, 405);
		g.drawString("-------------------------------------------", 320, 415);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString("Monks fished: " + Variables.monksCatched, 320, 435);
		g.drawString("Exp. gained: "
				+ (Skills.getExperience(Skills.FISHING) - Variables.startxp), 320, 450);
		final long xpPH = (long) (3600000d / (double) Variables.timer.getElapsed() * (double) (Skills
				.getExperience(Skills.FISHING) - Variables.startxp));
		g.drawString("Exp p/h: " + xpPH, 320, 465);
		g.drawString("Time running: " + Variables.timer.toElapsedString(), 320, 480);
		if (xpPH != 0) {
			final long timeTillLevel = (long) (((double) ((Skills
					.getExperienceToLevel(Skills.FISHING,
							Skills.getLevel(Skills.FISHING) + 1))) * 3600000.0) / (double) xpPH);
			g.drawString("TTL: " + Time.format(timeTillLevel), 320, 495);
		}
		
		g.drawLine(Mouse.getX(), 0, Mouse.getX(), 502);
		g.drawLine(0, Mouse.getY(), 762, Mouse.getY());
		
	}
	
}
