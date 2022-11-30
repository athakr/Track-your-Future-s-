import unittest
from engine import Engine
import pandas as pd

#Test1 = Engine(1, 1000) 
#Test1.residualDisplay() 
class Testing(unittest.TestCase):
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

if __name__ == '__main__':
    unittest.main()