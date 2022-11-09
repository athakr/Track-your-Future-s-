import numpy as np
import yfinance as yf
import pandas as pd


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

        alloc = pd.DataFrame(port_risk) #make a Dataframe

        #Take a specific row associated with given risk level
        personal_alloc = alloc.loc[risk-1]

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


Test1 = Engine(1, 1000)
Test1.residualDisplay()