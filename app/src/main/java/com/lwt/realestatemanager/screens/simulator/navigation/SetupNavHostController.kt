
package com.lwt.realestatemanager.screens.simulator.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.lwt.realestatemanager.screens.simulator.SharedViewModel
import com.lwt.realestatemanager.screens.simulator.calculator.LoanCalculationScreen
import com.lwt.realestatemanager.screens.simulator.details.DetailsScreen
import com.lwt.realestatemanager.screens.simulator.initial.InitialScreen
import com.lwt.realestatemanager.screens.simulator.utils.Constants


@Composable
fun SetupNavHostController(navController: NavHostController){
    val sharedViewModel: SharedViewModel = viewModel()


    NavHost(navController = navController, startDestination = Constants.DEST_INITIAL){
        //InitialScreen
        composable(Constants.DEST_INITIAL){
            InitialScreen(navController)
        }

        navigation(startDestination = Constants.DEST_INITIAL, Constants.GRAPH_SHARED){
            //LoanCalculationScreen
            composable(
                route = "${Constants.DEST_CALCULATION}/{${Constants.ARGS_IS_ANNUITY}}",
                arguments = listOf(navArgument(Constants.ARGS_IS_ANNUITY){type = NavType.BoolType})
            ){ backStackEntry ->
                LoanCalculationScreen(
                    navController = navController,
                    isAnnuityLoanType = backStackEntry.arguments?.getBoolean(Constants.ARGS_IS_ANNUITY) ?: true,
                    viewModel = sharedViewModel
                )
            }
            //DetailsScreen
            composable(Constants.DEST_DETAILS){
                DetailsScreen(
                    viewModel = sharedViewModel
                )
            }
        }
    }
}