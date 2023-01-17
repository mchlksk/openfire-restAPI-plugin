#!/bin/bash

XML="./plugin.xml"
XMLIN="./plugin.xml.in"
NEXTBUILD="./.next_build"
BUILDDIR="./target"
ARTEFACTIN="${BUILDDIR}/restAPI-openfire-plugin-assembly.jar"
ARTEFACT="restAPI.jar"

buildNo=$(head -n1 "$NEXTBUILD")
let nextBuildNo=$buildNo+1
echo $nextBuildNo >"$NEXTBUILD"
echo "+++ this build: ${buildNo} (next: ${nextBuildNo})"

echo "+++ prepare new plugin.xml"
rm "$XML"
cp "$XMLIN" "$XML" 
sed -i "s/__BUILD__/${buildNo}/" "$XML"

echo "+++ removing build dir"
rm -r "$BUILDDIR"

echo "+++ starting build"

mvn package

status=$?

if [[ $status -ne 0 ]]; then
    echo "+++ BUILD FAILED!"
    exit $status
fi

echo "+++ build successful: ${buildNo}"

deploy_dir="./deploy/current"
rm -r "${deploy_dir}"
mkdir -p "$deploy_dir"
backup_dir="./deploy/${buildNo}"
mkdir -p "$backup_dir"

echo "+++ collecting build artefact"

cp -v "$ARTEFACTIN" "${deploy_dir}/${ARTEFACT}"
cp -v "$ARTEFACTIN" "${backup_dir}/${ARTEFACT}"

