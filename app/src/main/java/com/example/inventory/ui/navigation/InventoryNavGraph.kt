/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventory.ui.home.HomeDestination
import com.example.inventory.ui.home.HomeScreen
import com.example.inventory.ui.item.ItemDetailsDestination
import com.example.inventory.ui.item.ItemDetailsScreen
import com.example.inventory.ui.item.ItemEditDestination
import com.example.inventory.ui.item.ItemEditScreen
import com.example.inventory.ui.item.ItemEntryDestination
import com.example.inventory.ui.item.ItemEntryScreen

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        /* ##################################################################################
        - Jeder dieser Screens hat ein Scaffold
        ################################################################################## */
        composable(route = HomeDestination.route) {
            /* ##################################################################################
            Der Aufruf von navController.navigate(ItemEntryDestination.route) navigiert direkt zu
            route => ItemEntryDestination.route, selbst wenn es in Log.i passiert

            * navigateToItemEntry ==> FloatingActionButton (onClick) ==> ItemEntryScreen

            * navigateToItemUpdate ==> Scaffold { HomeBody (onItemClick ==> navigateToItemUpdate)
                                                  ==> Column Default oder InventoryList

            ################################################################################## */

            // toDO: dises Werte nochmal verinnerlichen
            Log.i("INFO", "#### HomeDestination.route ####")
            Log.i("INFO", "#### -------------------------- ####")
            Log.i("INFO", "#### navigateToItemEntry ####")
            Log.i("INFO", "#### ItemEntryDestination.route ####")
            Log.i("INFO", "==>  ${ItemEntryDestination.route}")
            Log.i("INFO", "#### -------------------------- ####")
            Log.i("INFO", "#### -------------------------- ####")
            Log.i("INFO", "#### navigateToItemUpdate ####")
            Log.i("INFO", "#### ItemDetailsDestination.route ####")
            Log.i("INFO", "==>  ${ItemDetailsDestination.route}")
            Log.i("INFO", "#### it ####")
            Log.i("INFO", "==>  ${it.toString()}")

            // ToDO: Allgemein dieses "it" in composable() verstehen !?
            // ToDO: Allgemein dieses diese Screen übergene verstehen, wegen dieses it.arg.. !?

            HomeScreen(
                navigateToItemEntry = { navController.navigate(ItemEntryDestination.route) },
                navigateToItemUpdate = {
                    navController.navigate("${ItemDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = ItemEntryDestination.route) {
            ItemEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = ItemDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            /* ##################################################################################

            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg,
                                           builder = {type = NavType.IntType})
            ==> arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
                type = NavType.IntType
            }) - Die beiden Zeilen sind gleich.

            * route      ==> it.destination.route // alternative zu it -> backStackEntry -> (lambda)

            * arguments  ==> it.arguments
              - Ein direkter Zugriff auf "ItemDetailsDestination.itemIdArg":
                    ==> it.arguments?.getInt(ItemDetailsDestination.itemIdArg), getInt wegen "type"
              Bsp.: {  backStackEntry ->
                     val item = backStackEntry.arguments?.getString(..itemIdArg) ?: -9
                     // -9, Könnte zu einen Fehler führen, da es ein Index ist, vom Item.
                     // Das hier ist nur zum Verständis

            Link:
            - https://developer.android.com/reference/kotlin/android/os/Bundle
            ----------------------------------------------------------------------------------
            val temp  = it.arguments
            temp?.keySet()?.forEach { key ->
                Log.i("INFO", "- Key: $key, Value: ${temp.get(key)}")
            }
            Log.i("INFO", "#- Test = : ${temp?.getInt("itemId").toString()}")

            ################################################################################## */
            Log.i("INFO", "-----: ${ItemEditDestination.route}/${it}")
            ItemDetailsScreen(
                navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = ItemEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}
