package hd.dianelito;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Updater {

    private final JavaPlugin plugin;

    public Updater(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void checkForUpdates() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = new URL("https://api.github.com/Dianelito/Hardcore/releases/latest");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                StringBuilder result = new StringBuilder();
                int read;
                while ((read = inputStream.read()) != -1) {
                    result.append((char) read);
                }
                inputStream.close();

                // Parse JSON and get download URL (you'll need a JSON parser for this)
                // Example: String downloadUrl = "parsed_download_url_from_json";

                // Dummy download URL (replace with actual URL parsing)
                String downloadUrl = "https://github.com/Dianelito/Hardcore/releases/download/v1.0.0/Hardcore.jar";

                // Download new version
                downloadUpdate(downloadUrl);

                // Reload plugin
                Bukkit.getScheduler().runTask(plugin, () -> {
                    Bukkit.getServer().reload();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void downloadUpdate(String downloadUrl) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(downloadUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("plugins/Hardcore.jar")) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
