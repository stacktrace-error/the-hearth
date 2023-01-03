package hearth.entities.bases;

import arc.math.*;
import arc.struct.Seq;
import hearth.content.HUnits;
import hearth.type.ItemPayload;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;

public class HUnitEntity extends UnitEntity{
    public AhkarUnitType type = HUnits.daybreak;

    public boolean fortified = false;
    public float fortProgress = 0f;

    public Seq<ItemPayload> components = new Seq();

    //forting
    @Override
    public float speed(){
        float strafePenalty = isGrounded() || !isPlayer() ? 1f : Mathf.lerp(1f, type.strafePenalty, Angles.angleDist(vel().angle(), rotation) / 180f);
        float boost = Mathf.lerp(1f, type.canBoost ? type.boostMultiplier : 1f, elevation);
        float fortSpeedMult = Mathf.lerp(1f, type.fortSpeedMult, fortProgress);

        return type.speed * strafePenalty * boost * floorSpeedMultiplier() * fortSpeedMult;
    }

    @Override
    public void damage(float amount) {
        rawDamage(Damage.applyArmor(amount, armor) * type.fortArmorMult / healthMultiplier);
    }

    public void rawDamage(float amount) {
        boolean hadShields = shield > 1.0E-4F;
        if (hadShields) {
            shieldAlpha = 1.0F;
        }
        float shieldDamage = Math.min(Math.max(shield, 0), amount);
        shield -= shieldDamage;
        hitTime = 1.0F;
        amount -= shieldDamage;
        if (amount > 0 && type.killable) {
            health -= amount;
            if (health <= 0 && !dead) {
                kill();
            }
            if (hadShields && shield <= 1.0E-4F) {
                Fx.unitShieldBreak.at(x, y, 0, team.color, this);
            }
        }
    }

    //bs for existing purposes
    @Override
    public UnitType type() {
        return type;
    }

    public static HUnitEntity create(){
        return new HUnitEntity();
    }

    @Override
    public int classId(){
        return HUnits.classID(HUnitEntity.class);
    }
}
