import numpy as np
import yfinance as yf
import pandas as pd
from logging import ERROR
from json.decoder import JSONDecodeError

class Track:
    def __init__(self, stock):
        self.stock = stock
    def infob(self):
        self.data = yf.Ticker(self.stock)
        self.infor = self.data.info
        self.beta = 0

        for key in self.infor:
            if(key == 'beta'):
                self.beta = self.infor[key]

        return self.beta
    def infop(self):
        self.data = yf.Ticker(self.stock)
        self.infor = self.data.info
        self.price = 0

        for key in self.infor:
            if(key == 'currentPrice'):
                self.price = self.infor[key]

        return self.price


class Engine():
    def __init__(self, risk, dollars):
        
        #initializing key variables
        #*****************************************************************************************************************#
        # Risk is measured from 1 to 4
        # Conservative = 1
        # Balanced = 2
        # Growth = 3
        # Aggresive Growth = 4
        #*****************************************************************************************************************#
        if(risk not in range(1,5)):
            self.risk = 1
        else:
            self.risk = risk 
        #amount of money in USD up for investing
        if(dollars > 0):
            self.budget = dollars
        else:
            self.budget = 0
        #builds a base portfolio with associated risks amount for various risk types. index of [risk-1] is associated with the specific
        #risk listed above

        """self.cons
        self.bal
        self.growth
        self.agg_growth"""
        
        port_risk = {
            "US Stock":[0.14, 0.35, 0.49, 0.6],
            "Foreign Stock":[0.06, 0.15, 0.21, 0.25],
            "Bond": [0.5, 0.4, 0.25, 0.15],
            "Short-term investments":[0.3, 0.1, 0.05, 0]
        }

        self.alloc = pd.DataFrame(port_risk) #make a Dataframe

        #Take a specific row associated with given risk level
        personal_alloc = self.alloc.loc[self.risk-1]

        #make a portfolio based on risk and amount up for investment
        d_alloc = {
            "US Stock": [(personal_alloc.iloc[0] * self.budget)],
            "Foreign Stock": [(personal_alloc.iloc[1] * self.budget)],
            "Bond": [(personal_alloc.iloc[2] * self.budget)],
            "Short-term investments": [(personal_alloc.iloc[3] * self.budget)]
        }
        
        #make it into a Dataframe
        self.dol_alloc = pd.DataFrame(d_alloc)
    def getRisk(self):
        return self.risk
    
    def getDollarInvested(self):
        return self.budget

    def getDolAlloc(self):
        return self.dol_alloc

    def residualDisplay(self):
        "A method that returns the proper dollar investment in each investment type based on risk preference"
        if(self.budget < 101):
            print("You do not have enough money to invest properly")
        else:
            print(self.dol_alloc)
    def get_recommendation(self, stk_num):
        """Builds a recommendation based on the users risk and number of stocks"""
        self.stk_num = stk_num #number of stocks
        if(self.risk == 1):
            self.recommendation = self.build_rec(self.get_conservative_portfolio(), (self.budget - self.dol_alloc.loc[0].iloc[2]), self.stk_num)
            print(self.recommendation)
        elif(self.risk == 2):
            self.recommendation = self.build_rec(self.get_balanced_portfolio(), (self.budget - self.dol_alloc.loc[0].iloc[2]), self.stk_num)
            print(self.recommendation)
        elif(self.risk == 3):
            self.recommendation = self.build_rec(self.get_growth_portfolio(), (self.budget - self.dol_alloc.loc[0].iloc[2]), self.stk_num)
            print(self.recommendation)
        else:
            self.recommendation = self.build_rec(self.get_agg_growth_portfolio(), (self.budget - self.dol_alloc.loc[0].iloc[2]), self.stk_num)
            print(self.recommendation)

        return self.recommendation 

    def dollar_update(self, risk, dollars):
        if(risk not in range(1,5)):
            self.risk = 1
        else:
            self.risk = risk 
        #amount of money in USD up for investing
        if(dollars > 0):
            self.budget = dollars
        else:
            self.budget = 0
        personal_alloc = self.alloc.loc[self.risk-1]

        #make a portfolio based on risk and amount up for investment
        d_alloc = {
            "US Stock": [(personal_alloc.iloc[0] * self.budget)],
            "Foreign Stock": [(personal_alloc.iloc[1] * self.budget)],
            "Bond": [(personal_alloc.iloc[2] * self.budget)],
            "Short-term investments": [(personal_alloc.iloc[3] * self.budget)]
        }
        
        #make it into a Dataframe
        self.dol_alloc = pd.DataFrame(d_alloc)
        self.residualDisplay()
        return self.getDolAlloc()

    def update_beta(self):
        path1 = 'DAQ.csv'
        path2 = 'SP500.csv'
        path3 = 'DOW.csv'

        daq = pd.read_csv(path1)
        DAQ = pd.DataFrame(daq)
        DAQ = DAQ.drop(columns=["Description", 'Category2', 'Category3', 'GICS Sector', 'Country', 'Market cap', 'Action'])


        sp = pd.read_csv(path2)
        SP500 = pd.DataFrame(sp)
        SP500 = SP500.drop(columns=["Description", 'Category2', 'Category3', 'GICS Sector','Price to TTM earnings',
            'Price to TTM sales', 'Price to book value', 'Market cap', 'Action'])

        dow = pd.read_csv(path3)
        DOW = pd.DataFrame(dow)
        DOW = DOW.drop(columns=["Description", 'Category2', 'GICS Sector', 'Country', 'Market cap','Market Cap Weight', 'Index Weight', 'Action'])

        stock_list = pd.merge(DOW, DAQ, how='outer', left_on=["Symbol", 'Dividend yield'], right_on=["Symbol", 'Dividend yield'])
        stock_list = pd.merge(stock_list, SP500, how='outer', left_on=["Symbol", 'Dividend yield'], right_on=["Symbol", 'Dividend yield'])

        beta_list = np.array([])

        for i in range(0, len(stock_list)):
            try:
                k = stock_list['Symbol'][i]
                z = Track(k)
                m = z.infob()
                beta_list = np.append(beta_list, m)
            except TypeError:
                beta_list = np.append(beta_list, -100)

        stock_list['Beta'] = beta_list
        stock_list = stock_list.loc[(stock_list['Beta'] >= 0)]

        stock_list = stock_list.sort_values(by='Beta', ascending=True)
        stock_list.to_csv('stock_list.csv')
    def get_conservative_portfolio(self):
        path = 'stock_list.csv'
        stocks = pd.DataFrame(pd.read_csv(path))
        self.cons = stocks.loc[(stocks['Beta']<=0.6)]
        self.cons = self.cons.sort_values(by='Dividend yield', ascending=False)
        return self.cons
    def get_balanced_portfolio(self):
        path = 'stock_list.csv'
        stocks = pd.DataFrame(pd.read_csv(path))
        self.bal = stocks.loc[(stocks['Beta'] > 0.6) & (stocks['Beta'] <= 1.05)]
        self.bal = self.bal.sort_values(by='Dividend yield', ascending=False)
        return self.bal
    def get_growth_portfolio(self):
        path = 'stock_list.csv'
        stocks = pd.DataFrame(pd.read_csv(path))
        self.growth = stocks.loc[(stocks['Beta'] > 1.05) & (stocks['Beta'] <= 1.5)]
        self.growth = self.growth.sort_values(by='Dividend yield', ascending=False)
        return self.growth
    def get_agg_growth_portfolio(self):
        path = 'stock_list.csv'
        stocks = pd.DataFrame(pd.read_csv(path))
        self.agg_growth = stocks.loc[(stocks['Beta'] > 1.5)]
        self.agg_growth = self.agg_growth.sort_values(by='Dividend yield', ascending=False)
        return self.agg_growth
    def build_rec(self, stk_prt, dollars, stk_count):
        self.stk_prt = stk_prt #chosen portfolio risk
        self.inv = dollars #investment dollars
        rec_stock = np.array([])
        rec_price = np.array([])
        ek = self.stk_prt['Symbol']
        if(stk_count > len(ek)):
            el = len(ek)
        elif(stk_count <= 0):
            el = 1
        else:
            el = stk_count
        for i in range(0, el):
            rec_stock = np.append(rec_stock, ek.iloc[i])
        for i in range(0, len(rec_stock)):
            sym = rec_stock[i]
            sym_t = Track(sym)
            sym_in = sym_t.infop()
            rec_price = np.append(rec_price, sym_in)
        
        df = pd.DataFrame({'Symbol': rec_stock,
                            'price': rec_price})

        per_alloc = self.inv / el
        df['Dollars per stock'] = per_alloc
        df['Shares per stock'] = df.iloc[:,1].div(per_alloc).round(2)
        df['Shares per stock'] = (1/df['Shares per stock']).round(2)
        self.portfolio = df
        return self.portfolio

#Testing#
#Test1 = Engine(1, 1000) #test the first allocation with conservative risk and $1000
##Test1.residualDisplay() #display
#Test1.get_recommendation(5)
#Test1.dollar_update(2, 2000) #update to balanced allocation and $2000

