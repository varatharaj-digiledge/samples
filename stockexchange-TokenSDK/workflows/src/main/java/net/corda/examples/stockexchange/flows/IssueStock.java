package net.corda.examples.stockexchange.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.google.common.collect.ImmutableList;
import com.r3.corda.lib.tokens.contracts.states.FungibleToken;
import com.r3.corda.lib.tokens.contracts.types.IssuedTokenType;
import com.r3.corda.lib.tokens.workflows.flows.rpc.CreateEvolvableTokens;
import com.r3.corda.lib.tokens.workflows.flows.rpc.IssueTokens;
import net.corda.core.node.services.IdentityService;
import net.corda.examples.stockexchange.flows.utilities.ObserversUtilities;
import net.corda.examples.stockexchange.states.StockState;
import net.corda.core.contracts.Amount;
import net.corda.core.contracts.TransactionState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.InitiatingFlow;
import net.corda.core.flows.StartableByRPC;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Designed initiating node : Issuer
 * This flow issues a stock to the node itself just to keep things simple
 * ie. the issuer and the recipient of IssueTokens are the same
 * It first creates a StockState as EvovableTokenType and then issues some tokens base on this EvovableTokenType
 * The observer receives a copy of all of the transactions and records it in their vault
 */
@InitiatingFlow
@StartableByRPC
public class IssueStock extends FlowLogic<SignedTransaction> {

    private String symbol;
    private String name;
    private String currency;
    private int issueVol;

    // Using NetworkmapCache.getNotaryIdentities().get(0) is not encouraged due to multi notary is introduced
    private Party notary;

    public IssueStock(String symbol, String name, String currency, int issueVol, Party notary) {
        this.symbol = symbol;
        this.name = name;
        this.currency = currency;
        this.issueVol = issueVol;
        this.notary = notary;
    }

    @Override
    @Suspendable
    public SignedTransaction call() throws FlowException {

        // Sample specific - retrieving the hard-coded observers
        IdentityService identityService = getServiceHub().getIdentityService();
        List<Party> observers = ObserversUtilities.getObserverLegalIdenties(identityService);

        Party issuer = getOurIdentity();

        // Construct the output StockState
        final StockState stockState = new StockState(
                new UniqueIdentifier(),
                issuer,
                symbol,
                name,
                currency,
                BigDecimal.valueOf(0), // A newly issued stock should not have any dividend
                new Date(),
                new Date()
        );

        // The notary provided here will be used in all future actions of this token
        TransactionState<StockState> transactionState = new TransactionState<>(stockState, notary);

        // Using the build-in flow to create an evolvable token type -- Stock
        subFlow(new CreateEvolvableTokens(transactionState, observers));

        // Similar in IssueMoney flow, class of IssuedTokenType represents the stock is issued by the issuer party
        IssuedTokenType issuedStock = new IssuedTokenType(issuer, stockState.toPointer(stockState.getClass()));

        // Create an specified amount of stock with a pointer that refers to the StockState
        Amount<IssuedTokenType> issueAmount = new Amount(new Long(issueVol), issuedStock);

        // Indicate the recipient which is the issuing party itself here
        FungibleToken stockToken = new FungibleToken(issueAmount, getOurIdentity(), null);

        // Finally, use the build-in flow to issue the stock tokens. Observer parties provided here will record a copy of the transactions
        return subFlow(new IssueTokens(ImmutableList.of(stockToken), observers));
    }
}
