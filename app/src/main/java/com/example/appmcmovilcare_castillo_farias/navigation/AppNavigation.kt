package com.example.appmcmovilcare_castillo_farias.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appmcmovilcare_castillo_farias.ui.HomeScreen
import com.example.appmcmovilcare_castillo_farias.ui.screens.MisEquiposScreen
import com.example.appmcmovilcare_castillo_farias.ui.screens.RegistroScreen
import com.example.appmcmovilcare_castillo_farias.ui.screens.ResumenScreen
import com.example.appmcmovilcare_castillo_farias.ui.screens.SolicitarAtencionScreen
import com.example.appmcmovilcare_castillo_farias.viewmodel.NavigationViewModel
import com.example.appmcmovilcare_castillo_farias.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    anchoVentana: WindowWidthSizeClass,
    navigationViewModel: NavigationViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    val navController = rememberNavController()
    val lifecycleOwner = LocalLifecycleOwner.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentEntry.value?.destination?.route

    val destinos = listOf(
        AppRoutes.Inicio to "Inicio",
        AppRoutes.MisEquipos to "Mis equipos",
        AppRoutes.SolicitarAtencion to "Solicitar atención",
        AppRoutes.Registro to "Registro de usuario"
    )

    // Recibe los eventos enviados por NavigationViewModel.
    LaunchedEffect(navController, lifecycleOwner, navigationViewModel) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            navigationViewModel.navigationEvents.collect { event ->
                when (event) {
                    is NavigationEvent.NavigateTo -> {
                        navController.navigate(event.destination.route) {
                            // El resumen conserva el formulario detrás.
                            if (event.destination != AppRoutes.Resumen) {
                                popUpTo(AppRoutes.Inicio.route) {
                                    inclusive = false
                                }
                            }

                            launchSingleTop = true
                        }
                    }

                    NavigationEvent.NavigateBack -> {
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "MCmovil Care",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(24.dp)
                )

                HorizontalDivider()

                destinos.forEach { (destino, titulo) ->
                    NavigationDrawerItem(
                        label = {
                            Text(titulo)
                        },
                        selected = currentRoute == destino.route ||
                                (
                                        destino == AppRoutes.Registro &&
                                                currentRoute == AppRoutes.Resumen.route
                                        ),
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                navigationViewModel.navegarA(destino)
                            }
                        },
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 4.dp
                        )
                    )
                }
            }
        }
    ) {
        // Barra superior compartida por todas las pantallas.
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("MCmovil Care")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AppRoutes.Inicio.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
            ) {
                composable(AppRoutes.Inicio.route) {
                    HomeScreen(
                        anchoVentana = anchoVentana,
                        onMisEquipos = {
                            navigationViewModel.navegarA(AppRoutes.MisEquipos)
                        },
                        onSolicitarAtencion = {
                            navigationViewModel.navegarA(
                                AppRoutes.SolicitarAtencion
                            )
                        }
                    )
                }

                composable(AppRoutes.MisEquipos.route) {
                    MisEquiposScreen(
                        onVolver = {
                            navigationViewModel.volver()
                        },
                        onSolicitarAtencion = {
                            navigationViewModel.navegarA(
                                AppRoutes.SolicitarAtencion
                            )
                        }
                    )
                }

                composable(AppRoutes.SolicitarAtencion.route) {
                    SolicitarAtencionScreen(
                        onInicio = {
                            navigationViewModel.navegarA(AppRoutes.Inicio)
                        },
                        onMisEquipos = {
                            navigationViewModel.navegarA(AppRoutes.MisEquipos)
                        },
                        onVolver = {
                            navigationViewModel.volver()
                        }
                    )
                }

                // Ambas pantallas reciben la misma instancia.
                composable(AppRoutes.Registro.route) {
                    RegistroScreen(
                        usuarioViewModel = usuarioViewModel,
                        onVerResumen = {
                            navigationViewModel.navegarA(AppRoutes.Resumen)
                        }
                    )
                }

                composable(AppRoutes.Resumen.route) {
                    ResumenScreen(
                        usuarioViewModel = usuarioViewModel,
                        onEditar = {
                            navigationViewModel.volver()
                        },
                        onInicio = {
                            navigationViewModel.navegarA(AppRoutes.Inicio)
                        }
                    )
                }
            }
        }
    }
}