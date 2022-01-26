package creoii.origin.player;

public class PlayerController {
    private Player player;

    private float weaponUseCooldown = 0f;

    public PlayerController(Player player) {
        this.player = player;
    }

    protected void update(float deltaTime) {
        if (weaponUseCooldown > 0f) weaponUseCooldown -= deltaTime;
    }

    protected void useWeapon() {
        if (weaponUseCooldown <= 0f) {
            weaponUseCooldown = .5f;
        }
    }

    protected void useSigil() {

    }

    protected void useAbility() {

    }

    protected void useArmorAbility() {

    }

    protected void useConsumable() {

    }

    protected void useAccessoryAbility() {

    }
}
