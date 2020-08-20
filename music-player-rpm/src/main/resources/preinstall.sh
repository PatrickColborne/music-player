#!/usr/bin/env bash

/usr/sbin/useradd -c "Music Player" -U \
  -s /sbin/nologin -r \
  -d /var/music-player music-player 2> /dev/null || :
