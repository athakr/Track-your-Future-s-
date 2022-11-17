import numpy as np
import yfinance as yf
import pandas as pd
from logging import ERROR
from json.decoder import JSONDecodeError

class Track:
  def __init__(self, stock):
    self.stock = stock
    self.cons
    self.bal
    self.growth
    self.agg_growth
  def info(self):
    self.data = yf.Ticker(self.stock)
    self.infor = self.data.info
    self.beta = 0

    for key in self.infor:
      if(key == 'beta'):
        self.beta = self.infor[key]

    return self.beta

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
        
        self.risk = risk 
        #amount of money in USD up for investing
        self.budget = dollars
        #builds a base portfolio with associated risks amount for various risk types. index of [risk-1] is associated with the specific
        #risk listed above
        
        port_risk = {
            "US Stock":[0.14, 0.35, 0.49, 0.6],
            "Foreign Stock":[0.06, 0.15, 0.21, 0.25],
            "Bond": [0.5, 0.4, 0.25, 0.15],
            "Short-term investments":[0.3, 0.1, 0.05, 0]
        }

        self.alloc = pd.DataFrame(port_risk) #make a Dataframe

        #Take a specific row associated with given risk level
        personal_alloc = self.alloc.loc[risk-1]

        #make a portfolio based on risk and amount up for investment
        d_alloc = {
            "US Stock": [(personal_alloc.iloc[0] * self.budget)],
            "Foreign Stock": [(personal_alloc.iloc[1] * self.budget)],
            "Bond": [(personal_alloc.iloc[2] * self.budget)],
            "Short-term investments": [(personal_alloc.iloc[3] * self.budget)]
        }
        
        #make it into a Dataframe
        self.dol_alloc = pd.DataFrame(d_alloc)

    def residualDisplay(self):
        "A method that returns the proper dollar investment in each investment type based on risk preference"
        if(self.budget < 51):
            print("You do not have enough money to invest properly")
        else:
            print(self.dol_alloc)

    def dollar_update(self, risk, dollars):
        self.risk = risk
        self.budget = dollars
        personal_alloc = self.alloc.loc[risk-1]

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
                m = z.info()
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
    def get_balanced_portfolio(self):
        path = 'stock_list.csv'
        stocks = pd.DataFrame(pd.read_csv(path))
        self.bal = stocks.loc[(stocks['Beta'] > 0.6) & (stocks['Beta'] <= 1.05)]
        self.bal = self.bal.sort_values(by='Dividend yield', ascending=False)
    def get_growth_portfolio(self):
        path = 'stock_list.csv'
        stocks = pd.DataFrame(pd.read_csv(path))
        self.growth = stocks.loc[(stocks['Beta'] > 1.05) & (stocks['Beta'] <= 1.5)]
        self.growth = self.growth.sort_values(by='Dividend yield', ascending=False)
    def get_agg_growth_portfolio(self):
        path = 'stock_list.csv'
        stocks = pd.DataFrame(pd.read_csv(path))
        self.agg_growth = stocks.loc[(stocks['Beta'] > 1.5)]
        self.agg_growth = self.agg_growth.sort_values(by='Dividend yield', ascending=False)
#Testing#
Test1 = Engine(1, 1000)
Test1.residualDisplay()
Test1.dollar_update(2, 2000)