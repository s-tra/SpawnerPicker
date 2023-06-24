package online.vivaseikatsu.stra.spawnerpicker;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;

import java.awt.*;
import java.util.ArrayList;

public final class SpawnerPicker extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getServer().getPluginManager().registerEvents(this,this);
        getLogger().info("プラグインが有効になりました。");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("プラグインが無効になりました。");
    }


    // プレイヤーがブロックを壊したとき
    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e){

        // 壊されたのがスポナーだった場合のみ処理を実行
        if(e.getBlock().getType() != Material.SPAWNER) return;

        // プレーヤーを取得
        Player p = e.getPlayer();

        // ネザライトピッケルで壊した場合のみ続行
        if(p.getInventory().getItemInMainHand().getType() != Material.NETHERITE_PICKAXE) return;
        if(!p.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) return;

        // サバイバルじゃない場合終了
        if(p.getGameMode() != GameMode.SURVIVAL) return;

        // スポナーを取得
        CreatureSpawner original = (CreatureSpawner) e.getBlock().getState();
        // ドロップするスポナーを用意
        ItemStack drop = new ItemStack(Material.SPAWNER,1);
        // メタデータの取得
        ItemMeta meta = drop.getItemMeta();
        // 説明欄用の文字列リストを作成
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.WHITE + "Type: "+ original.getSpawnedType());
        // 説明欄をセット
        meta.setLore(lore);
        // アイテムにメタデータをセット
        drop.setItemMeta(meta);

        // ドロップする経験値を0に
        e.setExpToDrop(0);

        // 場所の取得
        Location l = e.getBlock().getLocation();

        // スポナーをドロップする
        if(l.getWorld() == null) return;
        l.getWorld().dropItemNaturally(l,drop);

    }

    // プレイヤーがブロックを設置したとき
    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e){


        if(e.getItemInHand().getType() != Material.SPAWNER) return;

        // 設置しようとしているブロックのメタデータを取得
        ItemMeta meta = e.getPlayer().getInventory().getItemInMainHand().getItemMeta();
        // nullをける
        if(meta == null) return;
        if(meta.getLore() == null) return;

        // EntityTypeを用意
        EntityType type;
        // スポナーを用意
        CreatureSpawner spawner = (CreatureSpawner) e.getBlockPlaced().getState();

        // 説明欄の走査
        for(String s : meta.getLore()){

            // Zombie
            if(s.contains(EntityType.ZOMBIE.toString())){
                type = EntityType.ZOMBIE;
                spawner.setSpawnedType(type);
                return;
            }

            // SKELETON
            if(s.contains(EntityType.SKELETON.toString())){
                type = EntityType.SKELETON;
                spawner.setSpawnedType(type);
                return;
            }

            // SPIDER
            if(s.contains(EntityType.SPIDER.toString())){
                type = EntityType.SPIDER;
                spawner.setSpawnedType(type);
                return;
            }

            // CAVE_SPIDER
            if(s.contains(EntityType.CAVE_SPIDER.toString())){
                type = EntityType.CAVE_SPIDER;
                spawner.setSpawnedType(type);
                return;
            }

            // SILVERFISH
            if(s.contains(EntityType.SILVERFISH.toString())){
                type = EntityType.SILVERFISH;
                spawner.setSpawnedType(type);
                return;
            }

            // BLAZE
            if(s.contains(EntityType.BLAZE.toString())){
                type = EntityType.BLAZE;
                spawner.setSpawnedType(type);
                return;
            }

            // MAGMA_CUBE
            if(s.contains(EntityType.MAGMA_CUBE.toString())){
                type = EntityType.MAGMA_CUBE;
                spawner.setSpawnedType(type);
                return;
            }

            // それ以外のとき
            type = EntityType.AREA_EFFECT_CLOUD;
            spawner.setSpawnedType(type);

            // 説明欄の走査ここまで
        }

    }













}



