from setuptools import setup, find_packages

setup(
    name='my_pytest',
    version='0.1',
    packages=find_packages(),
    entry_points={
        'console_scripts': [
            'my_pytests=simple_pytest:main',
        ],
    },
    python_requires='>=3.6',
)
