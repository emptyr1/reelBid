from __future__ import print_function
import argparse
from sys import stderr
from itertools import chain
import logging
import random

from .algorithms import reservoir_sample, approximate_sample, two_pass_sample
from .file_input import FileInput

logging.basicConfig(level=logging.DEBUG, format='LOG %(asctime)s > %(message)s', datefmt='%H:%M')

DEFAULT_FRACTION = 0.01
DEFAULT_SAMPLE_SIZE = 100

PERCENT = 100


