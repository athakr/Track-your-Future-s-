import unittest
from engine import Engine
from engine import Track
import pandas as pd
import yfinance as yf

#Test1 = Engine(1, 1000) 
#Test1.residualDisplay() 
class Testing(unittest.TestCase):
    
    def test_Track_beta(self):
        t = Track('TSLA')
        t_beta = t.infob()
        self.assertAlmostEqual(t_beta, 1.971345, 5, 'Passed!')

    def test_Track_price(self):
        t = Track('TSLA')
        t_price = t.infop()
        test_api_data = yf.Ticker('TSLA')
        t_info =test_api_data.info
        result = 0
        for key in t_info:
            if(key == 'currentPrice'):
                result = t_info[key]

        self.assertAlmostEqual(result, t_price, 1, "Pass!")

    def test_risk_alloc(self):
        risk = 1
        dollars = 1000.0
        portfolio = Engine(risk, dollars)
        r = portfolio.getRisk()
        self.assertEqual(r, 1)

    def test_risk_alloc_incorrect(self):
        risk = 8
        dollars = 1000.0
        portfolio = Engine(risk, dollars)
        r = portfolio.getRisk()
        self.assertEqual(r, 1)

    def test_dollars_alloc(self):
        risk = 1
        dollars = 1000.0
        portfolio = Engine(risk, dollars)
        d = portfolio.getDollarInvested()
        self.assertEqual(d, 1000.0)

    def test_dollars_alloc_incorrect(self):
        risk = 1
        dollars = -1000.0
        portfolio = Engine(risk, dollars)
        d = portfolio.getDollarInvested()
        self.assertEqual(d, 0.0)
    
    def test_updating(self):
        risk = 1
        dollars = 1000.0
        portfolio = Engine(risk, dollars)
        data = {'US Stock': [(dollars*0.35)],
                'Foreign Stock': [dollars *0.15],
                'Bond': [dollars * 0.4],
                'Short-term investments': [dollars*0.1]}
        df = pd.DataFrame(data)
        new_risk = 2
        c = portfolio.dollar_update(new_risk, dollars)
        boolean = df.equals(c)
        self.assertEqual(True, boolean)

    def test_updating_incRisk(self):
        risk = 1
        dollars = 1000.0
        portfolio = Engine(risk, dollars)
        data = {'US Stock': [(dollars*0.14)],
                'Foreign Stock': [dollars *0.06],
                'Bond': [dollars * 0.5],
                'Short-term investments': [dollars*0.3]}
        df = pd.DataFrame(data)
        new_risk = 9
        c = portfolio.dollar_update(new_risk, dollars)
        boolean = df.equals(c)
        self.assertEqual(True, boolean)

    def test_updating_incDollars(self):
        risk = 1
        dollars = 1000.0
        portfolio = Engine(risk, dollars)
        data = {'US Stock': [0.0],
                'Foreign Stock': [0.0],
                'Bond': [0.0],
                'Short-term investments': [0.0]}
        df = pd.DataFrame(data)
        new_dollars = -1000.0
        c = portfolio.dollar_update(risk, new_dollars)
        boolean = df.equals(c)
        self.assertEqual(True, boolean)

    def test_conservative_risk(self):
        risk = 1
        dollars = 1000.0
        portfolio = Engine(risk, dollars)
        c = portfolio.getDolAlloc()
        data = {'US Stock': [(dollars*0.14)],
                'Foreign Stock': [dollars *0.06],
                'Bond': [dollars * 0.5],
                'Short-term investments': [dollars*0.3]}
        df = pd.DataFrame(data)
        boolean = df.equals(c)
        self.assertEqual(True, boolean)

    def test_balanced_risk(self):
        risk = 2
        dollars = 1000.0
        portfolio = Engine(risk, dollars)
        c = portfolio.getDolAlloc()
        data = {'US Stock': [(dollars*0.35)],
                'Foreign Stock': [dollars *0.15],
                'Bond': [dollars * 0.4],
                'Short-term investments': [dollars*0.1]}
        df = pd.DataFrame(data)
        boolean = df.equals(c)
        self.assertEqual(True, boolean)
    def test_growth_risk(self):
        risk = 3
        dollars = 1000.0
        portfolio = Engine(risk, dollars)
        c = portfolio.getDolAlloc()
        data = {'US Stock': [(dollars*0.49)],
                'Foreign Stock': [dollars *0.21],
                'Bond': [dollars * 0.25],
                'Short-term investments': [dollars*0.05]}
        df = pd.DataFrame(data)
        boolean = df.equals(c)
        self.assertEqual(True, boolean)
    def test_aggGrowth_risk(self):
        risk = 4
        dollars = 1000.0
        portfolio = Engine(risk, dollars)
        c = portfolio.getDolAlloc()
        data = {'US Stock': [(dollars*0.6)],
                'Foreign Stock': [dollars *0.25],
                'Bond': [dollars * 0.15],
                'Short-term investments': [dollars*0]}
        df = pd.DataFrame(data)
        boolean = df.equals(c)
        self.assertEqual(True, boolean)
    def test_agg_growth_recommendation(self):
        risk = 4
        dollars = 1000.0
        portfolio = Engine(risk, dollars)
        df = portfolio.get_recommendation(5)
        test = pd.DataFrame({'Symbol': ['PXD', 'OKE', 'SPG', 'LNC', 'PARA']})
        test_bool = df['Symbol'].equals(test['Symbol'])
        self.assertEqual(test_bool, True)
    def test_growth_recommendation(self):
        risk = 3
        dollars = 1000.0
        portfolio = Engine(risk, dollars)
        df = portfolio.get_recommendation(5)
        test = pd.DataFrame({'Symbol': ['VNO', 'VFC', 'DOW', 'LYB', 'WMB']})
        test_bool = df['Symbol'].equals(test['Symbol'])
        self.assertEqual(test_bool, True)
    def test_bal_recommendation(self):
        risk = 2
        dollars = 1000.0
        portfolio = Engine(risk, dollars)
        df = portfolio.get_recommendation(5)
        test = pd.DataFrame({'Symbol': ['MO', 'NWL', 'T', 'KMI', 'PM']})
        test_bool = df['Symbol'].equals(test['Symbol'])
        self.assertEqual(test_bool, True)
    def test_con_recommendation(self):
        risk = 1
        dollars = 1000.0
        portfolio = Engine(risk, dollars)
        df = portfolio.get_recommendation(5)
        test = pd.DataFrame({'Symbol': ['VZ', 'PNW', 'NEM', 'D', 'DLR']})
        test_bool = df['Symbol'].equals(test['Symbol'])
        self.assertEqual(test_bool, True)
if __name__ == '__main__':
    unittest.main()