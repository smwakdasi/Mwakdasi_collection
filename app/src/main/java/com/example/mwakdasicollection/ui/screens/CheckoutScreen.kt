package com.example.mwakdasicollection.ui.screens

        import androidx.compose.foundation.layout.*
        import androidx.compose.material3.*
        import androidx.compose.runtime.*
        import androidx.compose.ui.Modifier
        import androidx.compose.ui.unit.dp
        import androidx.lifecycle.viewmodel.compose.viewModel
        import com.example.mwakdasicollection.manager.CartManager
        import com.example.mwakdasicollection.viewmodel.CheckoutViewModel
        import com.example.mwakdasicollection.viewmodel.OrderStatus
        import kotlinx.coroutines.launch

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun CheckoutScreen(
            userId: String,
            onOrderSuccess: () -> Unit,
            onCancel: () -> Unit,
            viewModel: CheckoutViewModel = viewModel()
        ) {
            val snackbarHostState = remember { SnackbarHostState() }
            val orderStatus by viewModel.orderStatus.collectAsState()
            val scope = rememberCoroutineScope()

            // Listen for order status changes to show a snackbar or navigate forward
            LaunchedEffect(orderStatus) {
                when (orderStatus) {
                    is OrderStatus.Success -> onOrderSuccess()
                    is OrderStatus.Error -> {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                (orderStatus as OrderStatus.Error).message
                            )
                        }
                    }
                    else -> Unit
                }
            }

            Scaffold(
                topBar = {
                    TopAppBar(title = { Text("Checkout") })
                },
                snackbarHost = { SnackbarHost(snackbarHostState) },
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Review Your Order",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Total Items: ${CartManager.getTotalItems()}")
                            Text("Total Cost: KSH ${"%.2f".format(CartManager.getTotalCost())}")
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = onCancel,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Cancel")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(
                                onClick = { viewModel.placeOrder(userId) },
                                modifier = Modifier.weight(1f),
                                enabled = CartManager.cartItems.isNotEmpty()
                            ) {
                                Text("Confirm Order")
                            }
                        }
                    }
                }
            )
        }