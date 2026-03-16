package entity;

public abstract class LivingEntity extends Entity {
	
	// Health stats
	public int maxHealth, currentHealth;
	
	// Universal method for taking damage
	public void takeDamage(int damage) {
		currentHealth -= damage;
	}
	
	// Universal method to check if the entity should be removed from the playground
	public boolean isDead() {
		return currentHealth <= 0;
	}
}
