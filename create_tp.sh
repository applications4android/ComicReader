#!/bin/bash
# COMMENTS:
#  Creates the .tp file out of the project directories.
#
# USAGE:
#  create_tp.sh [-h]
#    -h        Print this help and exit
#
# NOTE:
# . This script should be executed from TOT!
#


if [ "$1" = "-h" ]; then
    head -n6 $0 | sed -e 's/!\/bin\/bash/' -e 's/^\#//'
    exit 0
fi
version=`grep versionName ComicReader/AndroidManifest.xml | sed -e 's/.*=\"//' -e 's/\".*//'`
filename="ComicReader_$version.tp"
echo "Deleting the 'bin' directories ..."
rm -rf 'ComicReader/bin' 'ComicReaderTest/bin'
echo "Creating zip in the name of $filename ..."
"cd" ..
zip -q -r $filename 'ComicReader'
