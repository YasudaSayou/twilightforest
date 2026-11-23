package twilightforest.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.network.ParticlePacket;

import java.util.List;

public class FierySwordItem extends SwordItem {

	public FierySwordItem(Tier toolMaterial, Properties properties) {
		super(toolMaterial, properties);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!target.level().isClientSide() && !target.fireImmune()) {
			target.igniteForSeconds(15);
			ParticlePacket packet = new ParticlePacket();
			for (int var1 = 0; var1 < 20; ++var1) {
				double px = target.getX() + target.level().getRandom().nextFloat() * target.getBbWidth() * 2.0F - target.getBbWidth();
				double py = target.getY() + target.level().getRandom().nextFloat() * target.getBbHeight();
				double pz = target.getZ() + target.level().getRandom().nextFloat() * target.getBbWidth() * 2.0F - target.getBbWidth();
				packet.queueParticle(ParticleTypes.FLAME, false, px, py, pz, 0.02, 0.02, 0.02);
			}
			PacketDistributor.sendToPlayersTrackingEntityAndSelf(target, packet);
		}
		return super.hurtEnemy(stack, target, attacker);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
	}
}