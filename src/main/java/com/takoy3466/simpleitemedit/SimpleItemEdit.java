package com.takoy3466.simpleitemedit;

import com.takoy3466.simpleitemedit.command.SimpleAdminEditCommand;
import com.takoy3466.simpleitemedit.command.SimpleEditCommand;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContext;
import com.takoy3466.simpleitemedit.context.SimpleItemEditContextImpl;
import com.takoy3466.simpleitemedit.editor.*;
import com.takoy3466.simpleitemedit.editor.factory.*;
import com.takoy3466.simpleitemedit.input.ChatInputHandler;
import com.takoy3466.simpleitemedit.itemmodel.ItemModelRegistry;
import com.takoy3466.simpleitemedit.language.LanguageManager;
import com.takoy3466.simpleitemedit.listener.*;
import com.takoy3466.simpleitemedit.session.EditSessionListener;
import com.takoy3466.simpleitemedit.session.EditingSessionManager;
import com.takoy3466.simpleitemedit.util.EnchantLimitManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SimpleItemEdit extends JavaPlugin {

    private static final Logger log = LoggerFactory.getLogger(SimpleItemEdit.class);
    private static SimpleItemEdit instance;

    private EditorRegistry editorRegistry;
    private EditorGuiRegistry editorGuiRegistry;
    private EditingSessionManager sessionManager;
    private ChatInputHandler chatInputHandler;

    private SimpleItemEditContext context;
    private ItemModelRegistry itemModelRegistry;
    private EnchantLimitManager limitManager;
    private LanguageManager langManager;

    @Override
    public void onEnable() {
        instance = this;

        initialize();

        registerEditors();
        registerListeners();
        registerCommands();

        getLogger().info("SimpleItemEdit has been enabled.");
    }

    @Override
    public void onDisable() {
        if (sessionManager != null) {
            sessionManager.clear();
        }

        getLogger().info("SimpleItemEdit has been disabled.");
    }

    private void initialize() {
        sessionManager = new EditingSessionManager();
        editorRegistry = new EditorRegistry();
        editorGuiRegistry = new EditorGuiRegistry();
        chatInputHandler = new ChatInputHandler(this);

        itemModelRegistry = new ItemModelRegistry();
        itemModelRegistry.registerVanillaModels();
        itemModelRegistry.loadConfig(this);
        langManager = new LanguageManager(this);

        limitManager = new EnchantLimitManager(this);

        context = new SimpleItemEditContextImpl(this, editorRegistry, editorGuiRegistry, sessionManager, chatInputHandler, itemModelRegistry, limitManager, langManager);

    }

    private void registerEditors() {
        editorRegistry.register(new NameEditor());
        editorGuiRegistry.register(new NameGuiFactory(context));

        editorRegistry.register(new RarityEditor());
        editorGuiRegistry.register(new RarityGuiFactory(context));

        editorRegistry.register(new GlowEditor());
        editorGuiRegistry.register(new GlowGuiFactory(context));

        editorRegistry.register(new ColorEditor());
        editorGuiRegistry.register(new ColorGuiFactory(context));

        editorRegistry.register(new StyleEditor());
        editorGuiRegistry.register(new StyleGuiFactory(context));

        editorRegistry.register(new EquipmentSlotEditor());
        editorGuiRegistry.register(new EquipmentSlotGuiFactory(context));

        editorRegistry.register(new ItemModelEditor());
        editorGuiRegistry.register(new ItemModelGuiFactory(context));

        editorRegistry.register(new EnchantmentApplyEditor());
        editorGuiRegistry.register(new EnchantmentApplyGuiFactory(context));

        editorRegistry.register(new EnchantmentMergeEditor());
        editorGuiRegistry.register(new EnchantmentMergeGuiFactory(context));

        editorRegistry.register(new RgbColorEditor());
        editorGuiRegistry.register(new RgbColorGuiFactory(context));

        editorRegistry.register(new EnchantmentSplitEditor());
        editorGuiRegistry.register(new EnchantmentSplitGuiFactory(context));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new MainEditListener(editorGuiRegistry, sessionManager), this);
        getServer().getPluginManager().registerEvents(new NameGuiListener(context), this);
        getServer().getPluginManager().registerEvents(new RarityGuiListener(context), this);
        getServer().getPluginManager().registerEvents(new GlowGuiListener(context), this);
        getServer().getPluginManager().registerEvents(new ColorGuiListener(context), this);
        getServer().getPluginManager().registerEvents(new StyleGuiListener(context), this);
        getServer().getPluginManager().registerEvents(new EquipmentSlotGuiListener(context), this);
        getServer().getPluginManager().registerEvents(new ItemModelGuiListener(context), this);
        getServer().getPluginManager().registerEvents(new RgbColorGuiListener(context), this);
        getServer().getPluginManager().registerEvents(new EnchantmentApplyGuiListener(context), this);
        getServer().getPluginManager().registerEvents(new EnchantmentMergeGuiListener(context), this);
        getServer().getPluginManager().registerEvents(new EnchantmentSplitGuiListener(context), this);

        getServer().getPluginManager().registerEvents(chatInputHandler, this);
        getServer().getPluginManager().registerEvents(new EditSessionListener(sessionManager, chatInputHandler), this);
    }

    private void registerCommands() {

        SimpleEditCommand simpleEditCommand = new SimpleEditCommand(sessionManager, editorRegistry, limitManager);

        SimpleAdminEditCommand simpleAdminEditCommand = new SimpleAdminEditCommand();

        if (getCommand("simpleEdit") != null) {
            getCommand("simpleEdit").setExecutor(simpleEditCommand);
            getCommand("simpleEdit").setTabCompleter(simpleEditCommand);
        }

        if (getCommand("SimpleAdminEdit") != null) {
            getCommand("SimpleAdminEdit").setExecutor(simpleAdminEditCommand);
            getCommand("SimpleAdminEdit").setTabCompleter(simpleAdminEditCommand);
        }
    }

    public static SimpleItemEdit getInstance() {
        return instance;
    }

    public EditorRegistry getEditorRegistry() {
        return editorRegistry;
    }

    public EditorGuiRegistry getEditorGuiRegistry() {
        return editorGuiRegistry;
    }

    public EditingSessionManager getSessionManager() {
        return sessionManager;
    }

    public ChatInputHandler getChatInputHandler() {
        return chatInputHandler;
    }

    public SimpleItemEditContext getContext() {
        return context;
    }
}