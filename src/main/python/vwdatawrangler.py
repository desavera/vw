# This Python 3 environment comes with many helpful analytics libraries installed
# It is defined by the kaggle/python Docker image: https://github.com/kaggle/docker-python
# For example, here's several helpful packages to load

# Supress Warnings
import warnings
warnings.filterwarnings('ignore')

import numpy as np # linear algebra
import pandas as pd # data processing, CSV file I/O (e.g. pd.read_csv)

import matplotlib.pyplot as plt
import seaborn as sns

# visulaisation
from matplotlib.pyplot import xticks
%matplotlib inline

# Input data files are available in the read-only "../input/" directory
# For example, running this (by clicking run or pressing Shift+Enter) will list all files under the input directory

import os
for dirname, _, filenames in os.walk('/kaggle/input'):
    for filename in filenames:
        print(os.path.join(dirname, filename))

# You can write up to 5GB to the current directory (/kaggle/working/) that gets preserved as output when you create a version using "Save & Run All" 
# You can also write temporary files to /kaggle/temp/, but they won't be saved outside of the current session
# Any results you write to the current directory are saved as output.
# Data display coustomization
pd.set_option('display.max_rows', 100)
pd.set_option('display.max_columns', 100)

initdf = pd.read_csv("/kaggle/input/vwdataset/vwdataset.csv")
initdf.head(5)

# DATA CLEAN UP

# we will drop the columns having more than 75% NA values.
initdf = initdf.drop(initdf.loc[:,list(round(100*(initdf.isnull().sum()/len(initdf.index)), 2)>75)].columns, 1)
round(100*(initdf.isnull().sum()/len(initdf.index)), 2)

# Rest missing values are under 2% so we can drop these rows.
initdf.dropna(inplace = True)

#checking duplicates
sum(initdf.duplicated(subset ='policyID')) == 0

initdf.to_csv('vwdataset-wrangled.csv',index=False)

a, b, c = np.split(initdf, [int(.2*len(cleandf)), int(.5*len(cleandf))])

a.to_csv('vwdataset-wrangled1.csv',index=False)
b.to_csv('vwdataset-wrangled2.csv',index=False)
c.to_csv('vwdataset-wrangled3.csv',index=False)
