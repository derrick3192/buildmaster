set day=%1
set monthno=%2
set year=%3
set month=%4

set date=%day% %month% %year%
echo %date%
set dirch=C:\Arbiter\Src_Plugins_%year%_%monthno%_%day%_DNBU

:: Create the new directory.
mkdir %dirch%

cd %dirch%


set CVS_USER=%5

:: Directory to checkout from server.
set arbsrc=Arbiter\Src
:: The output directory of the checkout.
set outdir=%dirch%\Arbiter\Src

::Checkout the source folder.
echo -z9 -x -d :sspi:%CVS_USER%@cvs.arbsys.com:/CVSREPO checkout -P -D "%date%" -- %arbsrc% > nul
cvs -z9 -x -d :sspi:%CVS_USER%@cvs.arbsys.com:/CVSREPO checkout -P -D "%date%" -- %arbsrc% > nul

::Move the source to the correct place.
SET src_folder=%outdir%
SET tar_folder=%dirch%

for /f %%a IN ('dir "%src_folder%" /b') do move %src_folder%\%%a %tar_folder%

::Move the cvs hidden folder
SET cvs_folder=%dirch%\CVS
mkdir %tar_folder%\CVS
xcopy /H %dirch%\Arbiter\CVS %dirch%\CVS

::Delete the unwanted directory.
del /f/s/q %dirch%\Arbiter > nul
rmdir /s/q %dirch%\Arbiter

::Build?

::Checkout the Plugins folder
cvs -z9 -x -d :sspi:%CVS_USER%@cvs.arbsys.com:/CVSREPO checkout -P -D "%date%" -- Plugins > nul

::Build?

:: Return back to the directory
cd %ld%