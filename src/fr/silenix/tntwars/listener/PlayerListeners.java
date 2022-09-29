package fr.silenix.tntwars.listener;

import java.util.Arrays;







import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Enum.EtatPartie;
import Enum.EtatTNT;
import Fonctions.IndexKit;
import fr.silenix.tntwars.GMain;

import fr.silenix.tntwars.entity.Joueur;
import fr.silenix.tntwars.entity.Kit;
import fr.silenix.tntwars.tasks.TaskLancementPartie;
import fr.silenix.tntwars.tasks.TaskRejoindPartieEnCours;
import fr.silenix.tntwars.timer.TimerAllumageBlue;
import fr.silenix.tntwars.timer.TimerAllumageRed;
import fr.silenix.tntwars.timer.TimerInvisibility;

//import fr.silenix.tntwars.entity.Joueur;


public class PlayerListeners implements Listener{
	
	private GMain main;
	
	static World world = Bukkit.getWorld("world");


	public PlayerListeners(GMain main) {
		this.main = main;
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
     public void onJoin(PlayerJoinEvent event) {
		 Player player = event.getPlayer();
		 
		 
		 Joueur joueur = new Joueur(player,player.getName(),main.Sans_Equipe,main.list_kits.get(IndexKit.Sans_Kit),main.list_kits.get(IndexKit.Sans_Kit));
		 main.listeConnecte.add(joueur);
		 
		 
		 player.setInvulnerable(false);
		 player.setExp(0);
		 player.setInvisible(false);
		 
		 player.setFoodLevel(20);
		 player.setMaxHealth(20);
		 player.setHealth(20);
		 

		 player.getInventory().clear();
		 player.setScoreboard(main.board);
		 
		 
		 
		 
	 
		 if(!main.isState(EtatPartie.FinJeu)) { //si le joueur rejoint la partie avant la fin du jeu on lui donne le selectionneur d'�quipe
				
			 main.listeJoueurs.add(joueur);
			 	ItemStack customnetherstar = new ItemStack(Material.NETHER_STAR,1);
				ItemMeta customNS = customnetherstar.getItemMeta();
				customNS.setDisplayName("Choisir le kit");
				
				customNS.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 200, true);
				customNS.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				customNS.setLore(Arrays.asList("premiere ligne","deuxieme","troisieme"));
				
				customnetherstar.setItemMeta(customNS);
				player.getInventory().setItem(0,customnetherstar);
				
				

				
				
				
				
				/*ItemStack custombed = new ItemStack(Material.RED_BED,1);
				ItemMeta customB = custombed.getItemMeta();
				customB.setDisplayName("Retour au lobby");
				
				customB.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 200, true);
				customB.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				customB.setLore(Arrays.asList("premiere ligne","deuxieme","troisieme"));
				
				custombed.setItemMeta(customB);
				player.getInventory().setItem(0,custombed);*/
			
				
				
				
				
				if (!main.isState(EtatPartie.AttenteJoueur) && !main.isState(EtatPartie.FinJeu) && !main.isState(EtatPartie.Lancement)) {
					player.setGameMode(GameMode.ADVENTURE);
					player.setInvisible(true);
					player.sendMessage("�5[�dSilenixGames�5] �bLe jeux a d�ja d�marrer mais vous pouvez rejoindre une �quipe! ");
					event.setJoinMessage(null);
					
					
					ItemStack customnetherstars = new ItemStack(Material.NETHER_STAR,1);
					ItemMeta customNSs = customnetherstars.getItemMeta();
					customNSs.setDisplayName("Choisir le kit");
					customNSs.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 200, true);
					customNSs.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					customNSs.setLore(Arrays.asList("premiere ligne","deuxieme","troisieme"));
					customnetherstars.setItemMeta(customNSs);
					player.getInventory().setItem(0,customnetherstars);
					
					
					TaskRejoindPartieEnCours cycle = new TaskRejoindPartieEnCours(main, joueur);
					cycle.runTaskTimer(main, 0, 20);
					


					
					

				}
				
				
				
				
				
				if(main.isState(EtatPartie.AttenteJoueur) || main.isState(EtatPartie.Lancement)) {
				 	player.teleport(main.spawn_general);
				
				 	player.setGameMode(GameMode.ADVENTURE);
					event.setJoinMessage("�6[�eTntWars�6] �6" + player.getName()+ " �evient de se connecter � la partie.   �5<�d"+ main.listeJoueurs.size()+"�5/�d"+ Bukkit.getMaxPlayers()+"�5>");
				
				
					if(main.isState(EtatPartie.AttenteJoueur)  && main.listeJoueurs.size()  >= 2) {
							
						main.setState(EtatPartie.Lancement);
						TaskLancementPartie start = new TaskLancementPartie(main);
						start.runTaskTimer(main, 0, 20);
						
							
					}
					
					
				}
				
				if(main.isState(EtatPartie.Prejeu)) {
					
					player.setInvisible(false);
					player.teleport(main.map_en_cours.getLocationVisite());
					
					
					
					
					
					
					player.setGameMode(GameMode.ADVENTURE);
					event.setJoinMessage("�6[�eTntWars�6] �6" + player.getName()+ " �evient de se connecter � la partie.   �5<�d"+ main.listeJoueurs.size()+"�5/�d"+ Bukkit.getMaxPlayers()+"�5>");
				
				
				
				
						
				}
				
				
				
				
				
		 
		 
		 }
		 
		 
		 
		 if(main.isState(EtatPartie.FinJeu)) {
			 	player.setGameMode(GameMode.SPECTATOR);
			 	player.sendMessage("�6[�eTntWars�6] �eLe jeu est termin�, revenez pour la partie suivante !");
		 	}
		 
		 
		 
	 
	 
	 
	 
	 }
	 
	 @EventHandler
	 public void onQuit(PlayerQuitEvent event) {
			Player player = event.getPlayer();
			Joueur joueur = main.listeJoueurs.stream()
					  .filter(p -> player.getName().equals(p.getNom()))
					  .findAny()
					  .orElse(null);
			
			main.listeJoueurs.remove(joueur);
			main.listeConnecte.remove(joueur);
			player.setPlayerListName(player.getName());
			event.setQuitMessage(player.getName() + " est partie.");
			
			
			
		}
	 
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		BlockState block_replaced = event.getBlockReplacedState();
		
		if(!main.isState(EtatPartie.JeuEnCours)) {
			event.setCancelled(true);
			return;
		}else {
			if(event.getBlock().getType() == Material.RED_WOOL || event.getBlock().getType() == Material.BLUE_WOOL) {
				if(block_replaced.getType() == Material.WATER ) {
					event.setCancelled(true);
					return;
				}
				return;
			}else {
				event.setCancelled(true);
				return;
			}
		}
			
	}
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if(!main.isState(EtatPartie.JeuEnCours)) {
			event.setCancelled(true);
			return;
		}else {
			if(event.getBlock().getType() == Material.RED_WOOL || event.getBlock().getType() == Material.BLUE_WOOL) {
				return;
			}else {
				event.setCancelled(true);
				return;
			}
		}
		
		
	}
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		
		Inventory inv = event.getInventory();
		Player player = (Player) event.getWhoClicked();
		
		Joueur joueur = main.listeJoueurs.stream()
				  .filter(p -> player.getName().equals(p.getPlayer().getName()))
				  .findAny()
				  .orElse(null);
		
		
		
		ItemStack current = event.getCurrentItem();
		
		
		
		
		
		
		if (current==null) return;
		
		if(inv.contains(Material.RED_WOOL)) {
			if (current.getType()==Material.RED_WOOL) {
				player.closeInventory();
				main.RejoindEquipe(joueur, main.Equipe_rouge, main.map_en_cours.getNbEquipe());
			}else if (current.getType()==Material.BLUE_WOOL) {
				player.closeInventory();
				main.RejoindEquipe(joueur, main.Equipe_bleu, main.map_en_cours.getNbEquipe());
			}
		}
		
		
		
		
		if(inv.contains(Material.HEART_OF_THE_SEA) && inv.contains(Material.BEACON)) {
			
			
			
			Kit kit = main.list_kits.stream()
					.filter(k  ->  k.getItemSelection().getType().equals(current.getType()))
					.findFirst()
					.get();
			
			
			joueur.setProchainKit(kit); 
			player.closeInventory();
			player.sendMessage("�6[�eTntWars�6] �eKit "+ kit.getNom() +" s�lectionn�");
			

		}
	}

	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent event) {
		if(event.getRightClicked() instanceof Player)
        {
            Player player = event.getPlayer();
            Player victim = (Player) event.getRightClicked();
            
            Joueur joueur = main.listeJoueurs.stream()
					  .filter(p -> player.getName().equals(p.getNom()))
					  .findAny()
					  .orElse(null);
            Joueur joueur_victime = main.listeJoueurs.stream()
					  .filter(p -> victim.getName().equals(p.getNom()))
					  .findAny()
					  .orElse(null);
            
            /*if(!(player.getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD)) {
            	return;
            }*/
            
            /*if(player.getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD) {
            	if(main.TeamBlue.contains(player) && main.TeamBlue.contains(victim)) {
            		
            		if(victim.getHealth()>17.0) {
            			victim.setHealth(20.0);
            		}else {
            			victim.setHealth(victim.getHealth() + 1.0);
            		}
            		
            	}
            	
            	if(main.TeamRed.contains(player) && main.TeamRed.contains(victim)) {
            		
            		if(victim.getHealth()>17.0) {
            			victim.setHealth(20.0);
            		}else {
            			victim.setHealth(victim.getHealth() + 1.0);
            		}
            	}
            }*/
            
            if(joueur.getEquipe()== joueur_victime.getEquipe()) {
				
				if(((HumanEntity) player).getInventory().getItemInMainHand().getType() == Material.GLISTERING_MELON_SLICE) {
					if(joueur_victime.getKit()!=main.list_kits.get(IndexKit.OneShot)){
						if(joueur_victime.getPlayer().getHealth()>joueur_victime.getKit().getPointVie()-3) {
							victim.setHealth(joueur_victime.getKit().getPointVie());
	            		}else {
	            			victim.setHealth(victim.getHealth() + 2.0);
	            		}
					}
					return;
				}
				
			}
			

			
			
			
			
				
			if(((HumanEntity) player).getInventory().getItemInMainHand().getType() == Material.AMETHYST_CLUSTER) {
				((HumanEntity) player).getInventory().getItemInMainHand().setAmount(((HumanEntity) player).getInventory().getItemInMainHand().getAmount() - 1);
		        
				
				main.SpawnTeleportation(joueur);
				return;
			}
				

			

			
			
			if(joueur.getEquipe()!= joueur_victime.getEquipe()) {
				
				if(((HumanEntity) player).getInventory().getItemInMainHand().getType() == Material.INK_SAC) {
					
		            
					
					victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*15, 7));
					
					return;
				}
				
			}
			
			
            
            
			
            
        }
		
		
	}
	 
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
			
			Player player = event.getPlayer();
			Player player2 = event.getPlayer();
			//Action action = event.getAction();
			ItemStack it = event.getItem();
			Block block = event.getClickedBlock();
			
			
			Joueur joueur = main.listeJoueurs.stream()
					  .filter(p -> player2.getName().equals(p.getNom()))
					  .findAny()
					  .orElse(null);
			
			
			if (it==null) return;
			
			//setStateTntBlue(GStateTntBlue.BLUE_ETTEINTE);
			//setStateTntRed(GStateTntRed.RED_ETTEINTE);
			
			//Location tntblue = new Location (world, -5, 85, -9);
			
			
			if(it.getType() == Material.COMPASS /*&& it.getItemMeta().getDisplayName().equalsIgnoreCase("Selectionneur d'�quipe")*/){


				Inventory inv = Bukkit.createInventory(null, 27, "�7Choix d'�quipe");
				
				ItemStack customwoolteamblue = new ItemStack(Material.BLUE_WOOL,1);
				ItemMeta customWTB = customwoolteamblue.getItemMeta();
				customWTB.setDisplayName("Rejoindre l'�quipe bleu");
				
				//customWTB.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 200, true);
				//customWTB.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				customWTB.setLore(Arrays.asList("premiere ligne","deuxieme","troisieme"));
				
				customwoolteamblue.setItemMeta(customWTB);
				
				
				
				
				
				
				ItemStack customwoolteamred = new ItemStack(Material.RED_WOOL,1);
				ItemMeta customWTR = customwoolteamred.getItemMeta();
				customWTR.setDisplayName("Rejoindre l'�quipe rouge");
				
				//customWTR.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 200, true);
				//customWTR.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				customWTR.setLore(Arrays.asList("premiere ligne","deuxieme","troisieme"));
				
				customwoolteamred.setItemMeta(customWTR);
				
				inv.setItem(11, customwoolteamred);
				inv.setItem(14, customwoolteamblue);
				
				
				
				player.openInventory(inv);
			}
			
			
			
			
			
			
			/*if(it.getType() == Material.RED_BED) {
				

				
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				out.writeUTF("Connect");
				out.writeUTF("lobby");
				player.sendPluginMessage(main, "BungeeCord", out.toByteArray());
			}*/
			
			
			
			
			
			
			if(it.getType() == Material.NETHER_STAR) {
				Inventory inv2 = Bukkit.createInventory(null, 45, "�5Choix du kit");
				
				
				int emplacement = 0;

                for (Kit kit : main.list_kits) {

                    ItemStack customironsword = new ItemStack(kit.getItemSelection().getType(),1);
                    ItemMeta customIS = customironsword.getItemMeta();
                    customIS.setDisplayName("�5Kit "+ kit.getNom());
                    customIS.setLore(kit.getDescription());
                    customironsword.setItemMeta(customIS);

                    inv2.setItem(emplacement, customironsword);
                    emplacement++;
                }
				
				/*
				ItemStack customironsword = new ItemStack(Material.STONE_SWORD,1);
				ItemMeta customIS = customironsword.getItemMeta();
				customIS.setDisplayName("�5Kit Tank");
				customIS.setLore(Arrays.asList("�bArmure en diamand","�bEp�e en pierre","�dCe kit n'est pas fait pour la n�gociation.","�dLancez vous dans le combat avec pour �craser vos enemeies"));
				customironsword.setItemMeta(customIS);
								
				ItemStack customdiamondsword = new ItemStack(Material.DIAMOND_SWORD,1);
				ItemMeta customDS = customdiamondsword.getItemMeta();
				customDS.setDisplayName("�5Kit Guerrier");
				customDS.setLore(Arrays.asList("�bArmure en fer","�bEp�e en diamant","�dCe kit est fait pour les mordues de PVP.","�dVous ferez partie des combattants majeurs de votre �quipe."));
				customdiamondsword.setItemMeta(customDS);
				
				ItemStack custombow = new ItemStack(Material.BOW,1);
				ItemMeta customBOW = custombow.getItemMeta();
				customBOW.setDisplayName("�5Kit Archer");
				customBOW.setLore(Arrays.asList("�bArc enchant�","�bArmure en maille","�dLe kit archer est un soutien essentiel pour votre �quipe."));
				custombow.setItemMeta(customBOW);
				
				ItemStack customendereye = new ItemStack(Material.ENDER_EYE,1);
				ItemMeta customEE = customendereye.getItemMeta();
				customEE.setDisplayName("�5Kit Ghost");
				customEE.setLore(Arrays.asList("�bEp�e en bois","�bTotalement invisible","�dLe kit Ghost vous rendra invisivle pour ","�dinfiltrer les lignes enemies. Soyez discret et assasin."));
				customendereye.setItemMeta(customEE);
				
				ItemStack customglistering_melon_slice = new ItemStack(Material.GLISTERING_MELON_SLICE,1);
				ItemMeta customBlS = customglistering_melon_slice.getItemMeta();
				customBlS.setDisplayName("�5Kit Healer");
				customBlS.setLore(Arrays.asList("�bEp�e en pierre","�bArmure maille","�dLe kit des medecins, utilisez-le ","�dpour soigner vos compagnons."));
				customglistering_melon_slice.setItemMeta(customBlS);
				
				ItemStack customflintandsteel = new ItemStack(Material.FLINT_AND_STEEL,1);
				ItemMeta customFAS = customflintandsteel.getItemMeta();
				customFAS.setDisplayName("�5Kit Pyro");
				customFAS.setLore(Arrays.asList("�bEp�e en pierre","�bArmure fer","�dPour les pyroman du dimanche "));
				customflintandsteel.setItemMeta(customFAS);

				ItemStack customshears = new ItemStack(Material.SHEARS,1);
				ItemMeta customSHEARS = customshears.getItemMeta();
				customSHEARS.setDisplayName("�5Kit Builder");
				customSHEARS.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customshears.setItemMeta(customSHEARS);

				ItemStack customblaze = new ItemStack(Material.BLAZE_ROD,1);
				ItemMeta customSSS = customblaze.getItemMeta();
				customSSS.setDisplayName("�5Kit Sorcier");
				customSSS.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customblaze.setItemMeta(customSSS);

				ItemStack custompotion = new ItemStack(Material.POTION,1);
				ItemMeta customPOTION = custompotion.getItemMeta();
				customPOTION.setDisplayName("�5Kit Alchimiste");
				customPOTION.setLore(Arrays.asList("�btest","�btest","�dtest"));
				custompotion.setItemMeta(customPOTION);

				ItemStack customendermen = new ItemStack(Material.ENDER_PEARL,1);
				ItemMeta customENDERMEN = customendermen.getItemMeta();
				customENDERMEN.setDisplayName("�5Kit Endermen");
				customENDERMEN.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customendermen.setItemMeta(customENDERMEN);
				
				ItemStack customsnowball = new ItemStack(Material.SNOWBALL,1);
				ItemMeta customSNOWBALL = customsnowball.getItemMeta();
				customSNOWBALL.setDisplayName("�5Kit Snowman");
				customSNOWBALL.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customsnowball.setItemMeta(customSNOWBALL);

				ItemStack customendcrystal = new ItemStack(Material.END_CRYSTAL,1);
				ItemMeta customEC = customendcrystal.getItemMeta();
				customEC.setDisplayName("�5Kit Oneshot");
				customEC.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customendcrystal.setItemMeta(customEC);
				
				ItemStack customninja = new ItemStack(Material.BLACK_BANNER,1);
				ItemMeta customNINJA = customninja.getItemMeta();
				customNINJA.setDisplayName("�5Kit Ninja");
				customNINJA.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customninja.setItemMeta(customNINJA);

				ItemStack customamethystshard = new ItemStack(Material.AMETHYST_SHARD,1);
				ItemMeta customASHARD = customamethystshard.getItemMeta();
				customASHARD.setDisplayName("�5Kit Savior");
				customASHARD.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customamethystshard.setItemMeta(customASHARD);

				ItemStack custombarbare = new ItemStack(Material.DIAMOND_AXE,1);
				ItemMeta customBARBARE = custombarbare.getItemMeta();
				customBARBARE.setDisplayName("�5Kit Barbare");
				customBARBARE.setLore(Arrays.asList("�btest","�btest","�dtest"));
				custombarbare.setItemMeta(customBARBARE);

				ItemStack customtnt = new ItemStack(Material.GUNPOWDER,1);
				ItemMeta customTNT = customtnt.getItemMeta();
				customTNT.setDisplayName("�5Kit Creeper");
				customTNT.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customtnt.setItemMeta(customTNT);

				ItemStack custombone = new ItemStack(Material.BONE,1);
				ItemMeta customBONE = custombone.getItemMeta();
				customBONE.setDisplayName("�5Kit DogMaster");
				customBONE.setLore(Arrays.asList("�btest","�btest","�dtest"));
				custombone.setItemMeta(customBONE);
				
				ItemStack customskeleton = new ItemStack(Material.SKELETON_SKULL,1);
				ItemMeta customSKELETON = customskeleton.getItemMeta();
				customSKELETON.setDisplayName("�5Kit Pirate");
				customSKELETON.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customskeleton.setItemMeta(customSKELETON);
				
				ItemStack customsaddle = new ItemStack(Material.SADDLE,1);
				ItemMeta customSADDLE = customsaddle.getItemMeta();
				customSADDLE.setDisplayName("�5Kit Chevalier");
				customSADDLE.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customsaddle.setItemMeta(customSADDLE);
				
				ItemStack custompigrider = new ItemStack(Material.CARROT_ON_A_STICK,1);
				ItemMeta customPIGRIDER = custompigrider.getItemMeta();
				customPIGRIDER.setDisplayName("�5Kit PigRider");
				customPIGRIDER.setLore(Arrays.asList("�btest","�btest","�dtest"));
				custompigrider.setItemMeta(customPIGRIDER);

				ItemStack customtrident = new ItemStack(Material.TRIDENT,1);
				ItemMeta customTRIDENT = customtrident.getItemMeta();
				customTRIDENT.setDisplayName("�5Kit Trident");
				customTRIDENT.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customtrident.setItemMeta(customTRIDENT);
				
				ItemStack customelytra = new ItemStack(Material.ELYTRA,1);
				ItemMeta customELYTRA = customelytra.getItemMeta();
				customELYTRA.setDisplayName("�5Kit Elytra");
				customELYTRA.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customelytra.setItemMeta(customELYTRA);
				
				ItemStack customsquid = new ItemStack(Material.INK_SAC,1);
				ItemMeta customSQUID = customsquid.getItemMeta();
				customSQUID.setDisplayName("�5Kit Squid");
				customSQUID.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customsquid.setItemMeta(customSQUID);
				
				ItemStack customsgandalf = new ItemStack(Material.STICK,1);
				ItemMeta customgandalf = customsquid.getItemMeta();
				customgandalf.setDisplayName("�5Kit Gandalf");
				customgandalf.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customsgandalf.setItemMeta(customgandalf);
				
				ItemStack customshield = new ItemStack(Material.SHIELD,1);
				ItemMeta customSHIELD = customshield.getItemMeta();
				customSHIELD.setDisplayName("�5Kit Shield");
				customSHIELD.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customshield.setItemMeta(customSHIELD);
				
				ItemStack customarbalette = new ItemStack(Material.CROSSBOW,1);
				ItemMeta customARBALETTE = customarbalette.getItemMeta();
				customARBALETTE.setDisplayName("�5Kit Shield");
				customARBALETTE.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customarbalette.setItemMeta(customARBALETTE);
				
				ItemStack customshulker = new ItemStack(Material.SHULKER_BOX,1);
				ItemMeta customSHULKER = customshulker.getItemMeta();
				customSHULKER.setDisplayName("�5Kit Shulker");
				customSHULKER.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customshulker.setItemMeta(customSHULKER);
				
				ItemStack customghast = new ItemStack(Material.FIRE_CHARGE,1);
				ItemMeta customGHAST = customghast.getItemMeta();
				customGHAST.setDisplayName("�5Kit Ghast");
				customGHAST.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customghast.setItemMeta(customGHAST);
				
				ItemStack customspider = new ItemStack(Material.COBWEB,1);
				ItemMeta customSPIDER = customspider.getItemMeta();
				customSPIDER.setDisplayName("�5Kit Spider");
				customSPIDER.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customspider.setItemMeta(customSPIDER);
				
				ItemStack customwither = new ItemStack(Material.WITHER_SKELETON_SKULL,1);
				ItemMeta customWITHER = customwither.getItemMeta();
				customWITHER.setDisplayName("�5Kit Wither");
				customWITHER.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customwither.setItemMeta(customWITHER);
				
				ItemStack custompanda = new ItemStack(Material.BAMBOO,1);
				ItemMeta customPANDA = custompanda.getItemMeta();
				customPANDA.setDisplayName("�5Kit Panda");
				customPANDA.setLore(Arrays.asList("�btest","�btest","�dtest"));
				custompanda.setItemMeta(customPANDA);
				
				ItemStack custompoison = new ItemStack(Material.PUFFERFISH,1);
				ItemMeta customPOISON = custompoison.getItemMeta();
				customPOISON.setDisplayName("�5Kit Poison");
				customPOISON.setLore(Arrays.asList("�btest","�btest","�dtest"));
				custompoison.setItemMeta(customPOISON);
				
				ItemStack custompecheur = new ItemStack(Material.FISHING_ROD,1);
				ItemMeta customPECHEUR = custompecheur.getItemMeta();
				customPECHEUR.setDisplayName("�5Kit Pecheur");
				customPECHEUR.setLore(Arrays.asList("�btest","�btest","�dtest"));
				custompecheur.setItemMeta(customPECHEUR);
				
				ItemStack customrocket = new ItemStack(Material.FIREWORK_ROCKET,1);
				ItemMeta customROCKET = customrocket.getItemMeta();
				customROCKET.setDisplayName("�5Kit Rocketman");
				customROCKET.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customrocket.setItemMeta(customROCKET);
				
				ItemStack customblazek = new ItemStack(Material.BLAZE_POWDER,1);
				ItemMeta customBLAZE = customblazek.getItemMeta();
				customBLAZE.setDisplayName("�5Kit Blaze");
				customBLAZE.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customblazek.setItemMeta(customBLAZE);
				
				ItemStack customevoc = new ItemStack(Material.END_ROD,1);
				ItemMeta customEVOC = customevoc.getItemMeta();
				customEVOC.setDisplayName("�5Kit Evocateur");
				customEVOC.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customevoc.setItemMeta(customEVOC);
				
				ItemStack customarcher2 = new ItemStack(Material.ARROW,1);
				ItemMeta customARCHER2 = customarcher2.getItemMeta();
				customARCHER2.setDisplayName("�5Kit Archer Version 2");
				customARCHER2.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customarcher2.setItemMeta(customARCHER2);
				
				ItemStack customtacticien = new ItemStack(Material.WHITE_GLAZED_TERRACOTTA,1);
				ItemMeta customTACTICIEN = customtacticien.getItemMeta();
				customTACTICIEN.setDisplayName("�5Kit Tacticien");
				customTACTICIEN.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customtacticien.setItemMeta(customTACTICIEN);
				
				ItemStack customteleporteur = new ItemStack(Material.CLOCK,1);
				ItemMeta customTELEPORTEUR = customteleporteur.getItemMeta();
				customTELEPORTEUR.setDisplayName("�5Kit T�l�porteur");
				customTELEPORTEUR.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customteleporteur.setItemMeta(customTELEPORTEUR);
				
				ItemStack customspy = new ItemStack(Material.BEACON,1);
				ItemMeta customSPY = customspy.getItemMeta();
				customSPY.setDisplayName("�5Kit Espion");
				customSPY.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customspy.setItemMeta(customSPY);
				
				ItemStack customhasard = new ItemStack(Material.HEART_OF_THE_SEA,1);
				ItemMeta customHASARD = customhasard.getItemMeta();
				customHASARD.setDisplayName("�5Kit Hasard");
				customHASARD.setLore(Arrays.asList("�btest","�btest","�dtest"));
				customhasard.setItemMeta(customHASARD);
				
				
				
				inv2.setItem(0, customelytra);
				inv2.setItem(1, customtrident);
				inv2.setItem(2, custompigrider);
				inv2.setItem(3, customsaddle);
				inv2.setItem(4, customskeleton);
				inv2.setItem(5, custombone);
				inv2.setItem(6, customtnt);
				inv2.setItem(7, custombarbare);
				inv2.setItem(8, customninja);
				inv2.setItem(9, custompotion);
				inv2.setItem(10, customblaze);
				inv2.setItem(11, customflintandsteel);
				inv2.setItem(12, customglistering_melon_slice);
				inv2.setItem(13, customendereye);
				inv2.setItem(14, customironsword);
				inv2.setItem(15, customdiamondsword);
				inv2.setItem(16, custombow);
				inv2.setItem(17, customshears);
				inv2.setItem(18, customendermen);
				inv2.setItem(19, customsnowball);
				inv2.setItem(20, customendcrystal);
				inv2.setItem(21, customsquid);
				inv2.setItem(22, customamethystshard);
				inv2.setItem(23, customsgandalf);
				inv2.setItem(24, customshield);
				inv2.setItem(25, customarbalette);
				inv2.setItem(26, customshulker);
				inv2.setItem(27, customghast);
				inv2.setItem(28, customspider);
				inv2.setItem(29, customwither);
				inv2.setItem(30, custompanda);
				inv2.setItem(31, custompoison);
				inv2.setItem(32, custompecheur);
				inv2.setItem(33, customrocket);
				inv2.setItem(34, customblazek);
				inv2.setItem(35, customevoc);
				inv2.setItem(36, customarcher2);
				inv2.setItem(37, customtacticien);
				inv2.setItem(38, customteleporteur);
				inv2.setItem(39, customspy);
				inv2.setItem(40, customhasard);*/
				
				player.openInventory(inv2);
				
			}
			
			
			if(it.getType() == Material.GUNPOWDER) {
				World world = player.getWorld();
				world.createExplosion(player.getLocation(), 7, false, false);
			}
			
			if(it.getType() == Material.FIRE_CHARGE) {
				Fireball f = player.launchProjectile(Fireball.class);
				f.setIsIncendiary(false);
				f.setVelocity(f.getVelocity().multiply(50));
				f.setBounce(false);
				player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
			}
			

				if(it.getType() == Material.BLAZE_POWDER &&  event.getAction()==Action.RIGHT_CLICK_BLOCK ) {
				
					if (joueur.getEquipe() != main.Equipe_bleu  && (main.map_en_cours.getLocationTntArray(1).getX()+3)>block.getX() && block.getX()>(main.map_en_cours.getLocationTntArray(1).getX()-3)  && (main.map_en_cours.getLocationTntArray(1).getZ()+3)>block.getZ() && block.getZ()>(main.map_en_cours.getLocationTntArray(1).getZ()-3) ) {
						
						if (main.tnt_bleu.getEtat()==EtatTNT.Eteinte) {
							
							if (main.tnt_bleu.getVie()<main.Avancement_Max_Global_TNT && event.getClickedBlock().getType() == Material.TNT) {
								main.tnt_bleu.setVie(main.tnt_bleu.getVie()+1);
								//Bukkit.broadcastMessage("vie tnt bleu: "+ main.tnt_bleu.getVie()+"vie tnt rouge: "+ main.tnt_rouge.getVie());
			
								
								if(main.tnt_bleu.getVie() ==main.Avancement_Max_Global_TNT) {
									Bukkit.broadcastMessage("�6[�eTntWars�6] �4Attention! �eLa TNT �9bleue �ea �t� allum�e!");
									main.tnt_bleu.setEtat(EtatTNT.Allume);
									TimerAllumageBlue start = new TimerAllumageBlue(main);
									start.runTaskTimer(main, 0, 20);
								}
							}
						}
					}
				}
				
				
				
				
				if (it.getType() == Material.FEATHER && event.getAction()==Action.RIGHT_CLICK_BLOCK) {
					
					if (main.tnt_bleu.getEtat()==EtatTNT.Allume && joueur.getEquipe() == main.Equipe_bleu && (main.map_en_cours.getLocationTntArray(1).getX()+3)>block.getX() && block.getX()>(main.map_en_cours.getLocationTntArray(1).getX()-3)  && (main.map_en_cours.getLocationTntArray(1).getZ()+3)>block.getZ() && block.getZ()>(main.map_en_cours.getLocationTntArray(1).getZ()-3) ){
						
						if (main.tnt_bleu.getVie()>0 && event.getClickedBlock().getType() == Material.TNT) {
							main.tnt_bleu.setVie(main.tnt_bleu.getVie()-1);
							//Bukkit.broadcastMessage("vie tnt bleu: "+ main.tnt_bleu.getVie()+"vie tnt rouge: "+ main.tnt_rouge.getVie());
						
							if(main.tnt_bleu.getVie() ==0) {
								Bukkit.broadcastMessage("�6[�eTntWars�6] �eLa TNT �9bleue �ea �t� �tteinte.");
								main.tnt_bleu.setEtat(EtatTNT.Eteinte);
							}
						}
					}
				}
				
			
			
			
				if(it.getType() == Material.BLAZE_POWDER && event.getAction()==Action.RIGHT_CLICK_BLOCK   ) {
				
				  
				
					if (joueur.getEquipe() != main.Equipe_rouge && (main.map_en_cours.getLocationTntArray(0).getX()+3)>block.getX() && block.getX()>(main.map_en_cours.getLocationTntArray(0).getX()-3)  && (main.map_en_cours.getLocationTntArray(0).getZ()+3)>block.getZ() && block.getZ()>(main.map_en_cours.getLocationTntArray(0).getZ()-3)  ) {
						
						
						if (main.tnt_rouge.getEtat()==EtatTNT.Eteinte) {
							if (main.tnt_rouge.getVie()<main.Avancement_Max_Global_TNT && event.getClickedBlock().getType() == Material.TNT) {
								main.tnt_rouge.setVie(main.tnt_rouge.getVie()+1);
								//Bukkit.broadcastMessage("vie tnt bleu: "+ main.tnt_bleu.getVie()+"vie tnt rouge: "+ main.tnt_rouge.getVie());
							
								if(main.tnt_rouge.getVie() ==main.Avancement_Max_Global_TNT) {
									Bukkit.broadcastMessage("�6[�eTntWars�6] �4Attention! �eLa TNT �crouge �ea �t� allum�e!");
									main.tnt_rouge.setEtat(EtatTNT.Allume);
									TimerAllumageRed start = new TimerAllumageRed(main);
									start.runTaskTimer(main, 0, 20);
								}
							}
						}
					}
				}
				
				
				
				if (it.getType() == Material.FEATHER && event.getAction()==Action.RIGHT_CLICK_BLOCK  ) {
					
					
					if (main.tnt_rouge.getEtat()==EtatTNT.Allume && joueur.getEquipe() == main.Equipe_rouge &&  (main.map_en_cours.getLocationTntArray(0).getX()+3)>block.getX() && block.getX()>(main.map_en_cours.getLocationTntArray(0).getX()-3)  && (main.map_en_cours.getLocationTntArray(0).getZ()+3)>block.getZ() && block.getZ()>(main.map_en_cours.getLocationTntArray(0).getZ()-3)) {
						if (main.tnt_rouge.getVie()>0 && event.getClickedBlock().getType() == Material.TNT) {
							main.tnt_rouge.setVie(main.tnt_rouge.getVie()-1);
							//Bukkit.broadcastMessage("vie tnt bleu: "+ main.tnt_bleu.getVie()+"vie tnt rouge: "+ main.tnt_rouge.getVie());
						
							if(main.tnt_rouge.getVie() ==0) {
								Bukkit.broadcastMessage("�6[�eTntWars�6] �eLa TNT �crouge �ea �t� �tteinte.");
								main.tnt_rouge.setEtat(EtatTNT.Eteinte);
							}
						}
					}
				}
				
				if(it.getType() == Material.BLAZE_POWDER && joueur.getEquipe() != main.Equipe_jaune && event.getAction()==Action.RIGHT_CLICK_BLOCK && main.map_en_cours.getNbEquipe()>=4) {
					
					  
					
					if (event.getClickedBlock().getType() == Material.TNT && (main.map_en_cours.getLocationTntArray(3).getX()+3)>block.getX() && block.getX()>(main.map_en_cours.getLocationTntArray(3).getX()-3)  && (main.map_en_cours.getLocationTntArray(3).getZ()+3)>block.getZ() && block.getZ()>(main.map_en_cours.getLocationTntArray(3).getZ()-3)  ) {
						
						
						if (main.tnt_jaune.getEtat()==EtatTNT.Eteinte) {
							if (main.tnt_jaune.getVie()<main.Avancement_Max_Global_TNT) {
								main.tnt_jaune.setVie(main.tnt_jaune.getVie()+1);
							
								if(main.tnt_jaune.getVie() ==main.Avancement_Max_Global_TNT) {
									Bukkit.broadcastMessage("�6[�eTntWars�6] �4Attention! �eLa TNT �cjaune �ea �t� allum�e!");
									main.tnt_jaune.setEtat(EtatTNT.Allume);
									//TimerAllumageYellow start = new TimerAllumageYellow(main);
									//start.runTaskTimer(main, 0, 20);
								}
							}
						}
					}
				}
				
				
				
				if (it.getType() == Material.FEATHER && joueur.getEquipe() == main.Equipe_jaune && main.map_en_cours.getNbEquipe()>=4) {
					
					
					if (main.tnt_jaune.getEtat()==EtatTNT.Allume && event.getAction()==Action.RIGHT_CLICK_BLOCK &&  (main.map_en_cours.getLocationTntArray(3).getX()+3)>block.getX() && block.getX()>(main.map_en_cours.getLocationTntArray(3).getX()-3)  && (main.map_en_cours.getLocationTntArray(3).getZ()+3)>block.getZ() && block.getZ()>(main.map_en_cours.getLocationTntArray(3).getZ()-3)) {
						if (main.tnt_jaune.getVie()>0) {
							main.tnt_jaune.setVie(main.tnt_jaune.getVie()-1);
						
							if(main.tnt_jaune.getVie() ==0) {
								Bukkit.broadcastMessage("�6[�eTntWars�6] �eLa TNT �cjaune �ea �t� �tteinte.");
								main.tnt_jaune.setEtat(EtatTNT.Eteinte);
							}
						}
					}
				}
			
			
				if(it.getType() == Material.BLAZE_POWDER && joueur.getEquipe() != main.Equipe_vert && event.getAction()==Action.RIGHT_CLICK_BLOCK && main.map_en_cours.getNbEquipe()>=3) {
					
					  
					
					if (event.getClickedBlock().getType() == Material.TNT && (main.map_en_cours.getLocationTntArray(2).getX()+3)>block.getX() && block.getX()>(main.map_en_cours.getLocationTntArray(2).getX()-3)  && (main.map_en_cours.getLocationTntArray(2).getZ()+3)>block.getZ() && block.getZ()>(main.map_en_cours.getLocationTntArray(2).getZ()-3)  ) {
						
						
						if (main.tnt_vert.getEtat()==EtatTNT.Eteinte) {
							if (main.tnt_vert.getVie()<main.Avancement_Max_Global_TNT) {
								main.tnt_vert.setVie(main.tnt_vert.getVie()+1);
							
								if(main.tnt_vert.getVie() ==main.Avancement_Max_Global_TNT) {
									Bukkit.broadcastMessage("�6[�eTntWars�6] �4Attention! �eLa TNT �cjaune �ea �t� allum�e!");
									main.tnt_vert.setEtat(EtatTNT.Allume);
									//TimerAllumageGreen start = new TimerAllumageGreen(main);
									//start.runTaskTimer(main, 0, 20);
								}
							}
						}
					}
				}
				
				
				
				if (it.getType() == Material.FEATHER && joueur.getEquipe() == main.Equipe_vert && main.map_en_cours.getNbEquipe()>=3) {
					
					
					if (main.tnt_vert.getEtat()==EtatTNT.Allume && event.getAction()==Action.RIGHT_CLICK_BLOCK &&  (main.map_en_cours.getLocationTntArray(2).getX()+3)>block.getX() && block.getX()>(main.map_en_cours.getLocationTntArray(2).getX()-3)  && (main.map_en_cours.getLocationTntArray(2).getZ()+3)>block.getZ() && block.getZ()>(main.map_en_cours.getLocationTntArray(2).getZ()-3)) {
						if (main.tnt_vert.getVie()>0) {
							main.tnt_vert.setVie(main.tnt_vert.getVie()-1);
						
							if(main.tnt_vert.getVie() ==0) {
								Bukkit.broadcastMessage("�6[�eTntWars�6] �eLa TNT �cjaune �ea �t� �tteinte.");
								main.tnt_vert.setEtat(EtatTNT.Eteinte);
							}
						}
					}
				}

				if (it.getType() == Material.AMETHYST_SHARD && joueur.getKit()==main.list_kits.get(IndexKit.Savior)) {
					if(joueur.getEquipe()==main.Equipe_bleu) {
						if (player.getInventory().getItemInMainHand().getType() == Material.AMETHYST_SHARD) {
							player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
			            };
						player.teleport(main.map_en_cours.getLocationSpawnEquipeArray(1));
					}
					if(joueur.getEquipe()==main.Equipe_rouge) {
						if (player.getInventory().getItemInMainHand().getType() == Material.AMETHYST_SHARD) {
							player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
			            }
						player.teleport(main.map_en_cours.getLocationSpawnEquipeArray(0));
					}
					
				}
				
				
				if (it.getType() == Material.BLACK_BANNER && joueur.getKit()==main.list_kits.get(IndexKit.Ninja)) {
					player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
					TimerInvisibility start = new TimerInvisibility(main,player);
					start.runTaskTimer(main, 0, 20);
				}
			    
			
				
				
			
			
				
				
				
			
				
				
				
				
				
				
					
			
				
			
			
			
				

		    
			
			     
				
			

			
			
			
		}
	
	
	
}
