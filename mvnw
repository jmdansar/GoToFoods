#!/bin/sh

# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.

# ----------------------------------------------------------------------------
# Maven Wrapper bootstrap script
# ----------------------------------------------------------------------------

MVNW_REPOURL="https://repo.maven.apache.org/maven2"
WRAPPER_VERSION="3.3.2"

BASE_DIR=$(cd "$(dirname "$0")" && pwd)
WRAPPER_DIR="$BASE_DIR/.mvn/wrapper"
JAR="$WRAPPER_DIR/maven-wrapper.jar"
PROPS="$WRAPPER_DIR/maven-wrapper.properties"

mkdir -p "$WRAPPER_DIR"

if [ ! -f "$PROPS" ]; then
  cat > "$PROPS" <<EOF
distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.9/apache-maven-3.9.9-bin.zip
wrapperUrl=$MVNW_REPOURL/org/apache/maven/wrapper/maven-wrapper/$WRAPPER_VERSION/maven-wrapper-$WRAPPER_VERSION.jar
EOF
fi

# Download wrapper jar if missing
if [ ! -f "$JAR" ]; then
  WRAPPER_URL=$(grep '^wrapperUrl=' "$PROPS" | cut -d'=' -f2-)
  if [ -z "$WRAPPER_URL" ]; then
    WRAPPER_URL="$MVNW_REPOURL/org/apache/maven/wrapper/maven-wrapper/$WRAPPER_VERSION/maven-wrapper-$WRAPPER_VERSION.jar"
  fi

  if command -v curl >/dev/null 2>&1; then
    curl -fsSL "$WRAPPER_URL" -o "$JAR" || exit 1
  elif command -v wget >/dev/null 2>&1; then
    wget -q "$WRAPPER_URL" -O "$JAR" || exit 1
  else
    echo "ERROR: Neither curl nor wget is available to download Maven Wrapper." >&2
    exit 1
  fi
fi

JAVA_CMD="java"
if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ]; then
  JAVA_CMD="$JAVA_HOME/bin/java"
fi

exec "$JAVA_CMD" \
  -Dmaven.multiModuleProjectDirectory="$BASE_DIR" \
  -cp "$JAR" \
  org.apache.maven.wrapper.MavenWrapperMain \
  "$@"
