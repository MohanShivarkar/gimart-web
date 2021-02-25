package com.api.gimart.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.api.gimart.model.ApiListResponse;
import com.api.gimart.model.ApiResponse;
import com.api.gimart.model.wallet.CoinRequest;
import com.api.gimart.model.wallet.CoinTransListRequest;
import com.api.gimart.model.wallet.CoinTransRequest;
import com.api.gimart.model.wallet.generate.GenerateCoinListResponse;
import com.api.gimart.model.wallet.generate.GenerateCoinReqeust;
import com.api.gimart.repository.WalletRepository;
import com.api.gimart.service.WalletServices;

@Path("wallet")
public class WalletResources {

	WalletRepository repository;

	@POST
	@Path("/coin/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageCoins(CoinRequest request) {
		return WalletServices.getInstance().ManageCoins(request);
	}

	@GET
	@Path("/coin/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiListResponse getArticleDetails(@QueryParam("paction") int paction) {
		return WalletServices.getInstance().GetCoints(paction);
	}

	@POST
	@Path("/coin/generate/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse generateCoins(GenerateCoinReqeust request) {
		return WalletServices.getInstance().GenerateCoins(request);
	}

	@GET
	@Path("/coin/generate/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public GenerateCoinListResponse getGeneratedCoins(@QueryParam("paction") int paction,
			@QueryParam("pcoinid") int pcoinid) {
		return WalletServices.getInstance().GetGeneratedCoints(paction, pcoinid);
	}

	@POST
	@Path("/coin/trans/manage")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public ApiResponse manageCoinTrans(CoinTransRequest request) {
		return WalletServices.getInstance().ManageTransferCoins(request);
	}

	@POST
	@Path("/coin/trans/fetch")
	@Produces({ MediaType.APPLICATION_JSON })
	public ApiListResponse getCoinTrans(CoinTransListRequest request) {
		return WalletServices.getInstance().GetCoinTrans(request);
	}
}
