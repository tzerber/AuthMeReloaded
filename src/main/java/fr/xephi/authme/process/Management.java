package fr.xephi.authme.process;

import fr.xephi.authme.process.email.AsyncAddEmail;
import fr.xephi.authme.process.email.AsyncChangeEmail;
import fr.xephi.authme.process.join.AsynchronousJoin;
import fr.xephi.authme.process.login.AsynchronousLogin;
import fr.xephi.authme.process.logout.AsynchronousLogout;
import fr.xephi.authme.process.quit.AsynchronousQuit;
import fr.xephi.authme.process.register.AsyncRegister;
import fr.xephi.authme.process.unregister.AsynchronousUnregister;
import fr.xephi.authme.util.BukkitService;
import org.bukkit.entity.Player;

import javax.inject.Inject;


public class Management {

    @Inject
    private BukkitService bukkitService;

    // Processes
    @Inject
    private AsyncAddEmail asyncAddEmail;
    @Inject
    private AsyncChangeEmail asyncChangeEmail;
    @Inject
    private AsynchronousLogout asynchronousLogout;
    @Inject
    private AsynchronousQuit asynchronousQuit;
    @Inject
    private AsynchronousJoin asynchronousJoin;
    @Inject
    private AsyncRegister asyncRegister;
    @Inject
    private AsynchronousLogin asynchronousLogin;
    @Inject
    private AsynchronousUnregister asynchronousUnregister;

    Management() { }

    public void performLogin(final Player player, final String password, final boolean forceLogin) {
        runTask(new Runnable() {
            @Override
            public void run() {
                asynchronousLogin.login(player, password, forceLogin);
            }
        });
    }

    public void performLogout(final Player player) {
        runTask(new Runnable() {
            @Override
            public void run() {
                asynchronousLogout.logout(player);
            }
        });
    }

    public void performRegister(final Player player, final String password, final String email, final boolean autoLogin) {
        runTask(new Runnable() {
            @Override
            public void run() {
                asyncRegister.register(player, password, email, autoLogin);
            }
        });
    }

    public void performUnregister(final Player player, final String password, final boolean isForce) {
        runTask(new Runnable() {
            @Override
            public void run() {
                asynchronousUnregister.unregister(player, password, isForce);
            }
        });
    }

    public void performJoin(final Player player) {
        runTask(new Runnable() {
            @Override
            public void run() {
                asynchronousJoin.processJoin(player);
            }
        });
    }

    public void performQuit(final Player player, final boolean isKick) {
        runTask(new Runnable() {
            @Override
            public void run() {
                asynchronousQuit.processQuit(player, isKick);
            }
        });
    }

    public void performAddEmail(final Player player, final String newEmail) {
        runTask(new Runnable() {
            @Override
            public void run() {
                asyncAddEmail.addEmail(player, newEmail);
            }
        });
    }

    public void performChangeEmail(final Player player, final String oldEmail, final String newEmail) {
        runTask(new Runnable() {
            @Override
            public void run() {
                asyncChangeEmail.changeEmail(player, oldEmail, newEmail);
            }
        });
    }

    private void runTask(Runnable runnable) {
        bukkitService.runTaskAsynchronously(runnable);
    }
}
