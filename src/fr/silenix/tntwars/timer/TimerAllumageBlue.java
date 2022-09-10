package fr.silenix.tntwars.timer;

import org.bukkit.Bukkit;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import Enum.EtatPartie;
import Enum.EtatTNT;
import fr.silenix.tntwars.entity.Joueur;


public class TimerAllumageBlue extends BukkitRunnable{
	
	
	private fr.silenix.tntwars.main main;
	
	
	
	
	
	public TimerAllumageBlue(fr.silenix.tntwars.main main) {
		this.main = main;
	}
	
	int timer_blue= main.Timer_Global_TNT;
	
	
	@Override
	public void run() {
		if(main.tnt_bleu.getEtat()==EtatTNT.Eteinte){
			Bukkit.broadcastMessage("�6[�eTntWars�6] �eExplosion �98bleue  �e�vit�e.");
			
			timer_blue= main.Timer_Global_TNT;
			
			
			cancel();
		}
		if(main.isState(EtatPartie.FinJeu)) {
			cancel();
		} 
		if (timer_blue==main.Timer_Global_TNT && main.tnt_bleu.getEtat()==EtatTNT.Allume) {
			Bukkit.broadcastMessage("�6[�eTntWars�6] �eIl reste �630 �b secondes avant l'explosion de la TNT �9bleue�e.");
			for(int i=0; i < main.listeJoueurs.size(); i++) {
				Joueur joueur = main.listeJoueurs.get(i);
				joueur.getPlayer().playSound(joueur.getPlayer(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F);
			}
		}
		if (timer_blue==20) {
			Bukkit.broadcastMessage("�6[�eTntWars�6] �eIl reste �620 �esecondes avant l'explosion de la TNT �9bleue�e.");
		}
		if (timer_blue==10) {
			Bukkit.broadcastMessage("�6[�eTntWars�6] �eIl reste �610 �esecondes avant l'explosion de la TNT �9bleue�e.");
		}
		if (timer_blue==5) {
			Bukkit.broadcastMessage("�6[�eTntWars�6] �eIl reste �65 �esecondes avant l'explosion de la TNT �9bleue�e.");
		}
		if (timer_blue==4) {
			Bukkit.broadcastMessage("�6[�eTntWars�6] �eIl reste �64 �esecondes avant l'explosion de la TNT �9bleue�e.");
		}
		if (timer_blue==3) {
			Bukkit.broadcastMessage("�6[�eTntWars�6] �eIl reste �63 �esecondes avant l'explosion de la TNT �9bleue�e.");
		}
		if (timer_blue==2) {
			Bukkit.broadcastMessage("�6[�eTntWars�6] �eIl reste �62 �esecondes avant l'explosion de la TNT �9bleue�e.");
		}
		if (timer_blue==1) {
			Bukkit.broadcastMessage("�6[�eTntWars�6] �eIl reste �61 �esecondes avant l'explosion de la TNT �9bleue�e.");
		}
		if (timer_blue==0) {
			

			
			main.tnt_bleu.setEtat(EtatTNT.Explose);
			main.CheckWin();
				
			Bukkit.getWorld("world").spawnParticle(Particle.EXPLOSION_HUGE, main.map_en_cours.getLocationTntArray(1),100, 1,1,1 );
			
			for(int i=0; i < main.listeJoueurs.size(); i++) {
				Joueur joueur = main.listeJoueurs.get(i);
				
				
					
				joueur.getPlayer().playSound(main.map_en_cours.getLocationTntArray(1), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 3F, 1F);
				
			}
			
			cancel();
			
			
		}
		
		if (timer_blue!=0) {
			timer_blue--;
		}
		
		
		
		
		Bukkit.getWorld("world").spawnParticle(Particle.LAVA, main.map_en_cours.getLocationTntArray(1),100, 1,1,1 );
			
		
		for(int i=0; i < main.listeJoueurs.size(); i++) {
			Joueur joueur = main.listeJoueurs.get(i);
			
			
				
			joueur.getPlayer().playSound(main.map_en_cours.getLocationTntArray(1), Sound.ENTITY_CREEPER_PRIMED, 0.6F, 0.6F);
			
			
			//main.scoreboard(player);
		}
		
	}
	
	

}