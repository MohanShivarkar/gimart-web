package com.api.gimart.repository;

import com.api.gimart.model.ApiListResponse;
import com.api.gimart.model.ApiResponse;
import com.api.gimart.model.wallet.CoinRequest;
import com.api.gimart.model.wallet.CoinTransListRequest;
import com.api.gimart.model.wallet.CoinTransRequest;

public interface WalletRepository {

	// MasterCoins
	ApiListResponse GetCoints(int paction);

	ApiResponse ManageCoins(CoinRequest request);

	// TransCoins
	ApiResponse ManageTransferCoins(CoinTransRequest request);

	ApiListResponse GetCoinTrans(CoinTransListRequest request);
}
