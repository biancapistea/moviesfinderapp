#!/usr/bin/env bash

bash_is_current_version() {
  bash --version | grep -q 'version 5'
}

curl_is_installed() {
  &>/dev/null which curl &&
    curl --version | grep -q '^curl'
}

get_current_date() {
  curl --silent --insecure -I google.com |
    grep -E '^Date' |
    sed 's/^Date: //' 
      xargs -I {} date --date='{}'
}

if ! bash_is_current_version
then
  >&2 echo "ERROR: Bash not installed or not the right version."
  exit 1
fi

if ! curl_is_installed
then
  >&2 echo "ERROR: Curl is not installed."
  exit 1
fi

echo "Hello! The current date and time is $(get_current_date)"
