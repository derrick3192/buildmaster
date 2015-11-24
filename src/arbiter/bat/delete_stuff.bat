FOR %%A in (%*) DO (
del /f/s/q %%A > nul
rmdir /s/q %%A > nul
)