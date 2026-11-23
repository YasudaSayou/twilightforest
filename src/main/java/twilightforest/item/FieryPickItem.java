package twilightforest.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.network.ParticlePacket;

import java.util.List;

public class FieryPickItem extends PickaxeItem {

	public FieryPickItem(Tier toolMaterial, Properties properties) {
		super(toolMaterial, properties);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!target.level().isClientSide() && !target.fireImmune()) {
			target.igniteForSeconds(15);
			ParticlePacket packet = new ParticlePacket();
			packet.queueParticle(ParticleTypes.FLAME, false, target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ(), target.getBbWidth() * 0.5, target.getBbHeight() * 0.5, target.getBbWidth() * 0.5);
			PacketDistributor.sendToPlayersTrackingEntityAndSelf(target, packet);
		}
		return super.hurtEnemy(stack, target, attacker);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
		super.appendHoverText(stack, context, tooltip, flags);
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
	}
}