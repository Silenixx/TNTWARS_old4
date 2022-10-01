package fr.silenix.tntwars.entity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class Kit {

	private String Nom;
	private ItemStack Helmet;
	private ItemStack Chestplate;
	private ItemStack Legging;
	private ItemStack Boots;
	private ItemStack MainWeapon;
	private int PointVie;
	private ArrayList<ItemStack> ItemsArray;
	private PotionEffect Potioneffect;
	private boolean IsVisible;
	private ItemStack ItemSelection;
	private List<String> Description;
	private String DroitAcces;
	
	
	
	public Kit(String Nom, ItemStack Helmet, ItemStack Chestplate, ItemStack Legging,ItemStack Boots,ItemStack MainWeapon,int PointVie, ArrayList<ItemStack> ItemsArray, PotionEffect Potioneffect, boolean IsVisible,ItemStack ItemSelection, List<String>  description, String DroitAcces) {
        super();
        this.Nom=Nom;
        this.Helmet=Helmet;
        this.Chestplate=Chestplate;
        this.Legging=Legging;
        this.Boots=Boots ;
        this.MainWeapon=MainWeapon;
        this.PointVie=PointVie;
        this.ItemsArray=ItemsArray;
        this.Potioneffect=Potioneffect;
        this.IsVisible=IsVisible;
        this.ItemSelection=ItemSelection;
        this.Description = description;
        this.DroitAcces = DroitAcces;
    }
	
	
	
	
	public String getDroitAcces() {
		return DroitAcces;
	}
	public void setDroitAcces(String droitAcces) {
		DroitAcces = droitAcces;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public ItemStack getHelmet() {
		return Helmet;
	}
	public void setHelmet(ItemStack helmet) {
		Helmet = helmet;
	}
	public ItemStack getChestplate() {
		return Chestplate;
	}
	public void setChestplate(ItemStack chestplate) {
		Chestplate = chestplate;
	}
	public ItemStack getLegging() {
		return Legging;
	}
	public void setLegging(ItemStack legging) {
		Legging = legging;
	}
	public ItemStack getMainWeapon() {
		return MainWeapon;
	}
	public void setMainWeapon(ItemStack mainweapon) {
		MainWeapon = mainweapon;
	}
	public ItemStack getBoots() {
		return Boots;
	}
	public void setBoots(ItemStack boots) {
		Boots = boots;
	}
	public float getPointVie() {
		return PointVie;
	}
	public void setPointVie(int pointVie) {
		PointVie = pointVie;
	}
	public ArrayList<ItemStack> getItemsArray() {
		return ItemsArray;
	}
	public void setItemsArray(ArrayList<ItemStack> itemsArray) {
		ItemsArray = itemsArray;
	}
	public PotionEffect getPotioneffect() {
		return Potioneffect;
	}
	public void setPotioneffect(PotionEffect potioneffect) {
		Potioneffect = potioneffect;
	}
	public boolean IsVisible() {
		return IsVisible;
	}
	public void setIsVisible(boolean isVisible) {
		IsVisible = isVisible;
	}
	public ItemStack getItemSelection() {
		return ItemSelection;
	}
	public void setItemSelection(ItemStack Selection) {
		ItemSelection = Selection;
	}
	
	public List<String>  getDescription() {
        return Description;
    }
    public void setDescription(List<String>  description) {
        Description = description;
    }


}
