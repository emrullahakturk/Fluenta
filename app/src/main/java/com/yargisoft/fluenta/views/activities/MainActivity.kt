package com.yargisoft.fluenta.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navHostNavController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.drawerLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navHostNavController = navHostFragment.navController

        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainPageFragment,
                R.id.oxfordWordsFragment,
                R.id.mostCommonWordsFragment,
                R.id.mostCommonPhrasesFragment,
                R.id.listenAndLearnFragment,
                R.id.aiTutorFragment,
                R.id.translatorFragment,
                R.id.favoritesFragment,
                R.id.upgradeProFragment,
                R.id.myAccountFragment,
                R.id.settingsFragment,
                R.id.feedbackFragment,
                R.id.aboutUsFragment,
            ),
            drawerLayout
        )

        setupActionBarWithNavController(navHostNavController, appBarConfiguration)
        navigationView.setupWithNavController(navHostNavController)


        val drawerToggle = androidx.appcompat.app.ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()


        navHostNavController.addOnDestinationChangedListener { _, _, _ ->
            drawerToggle.isDrawerIndicatorEnabled = true
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.logout) {
                // Logout tıklandığında oturum kapatılır
                showLogoutConfirmationDialog()
                true
            } else {
                /* else case'i içerisinde navigation drawer'ın normal davranışını geri
                kazandırıyoruz. Aksi takdirde navigation işlemleri çalışmıyor.*/
                NavigationUI.onNavDestinationSelected(menuItem, navHostNavController)
                binding.drawerLayout.closeDrawers()
                true
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                logout()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun logout() {
        firebaseAuth.signOut() // Oturum kapatılır
        val intent = Intent(this, AuthActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Önceki activity'leri kapat
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navHostNavController.navigateUp(appBarConfiguration)
    }

}
