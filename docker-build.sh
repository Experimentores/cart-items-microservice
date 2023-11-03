#!/usr/bin/env bash
name=tripstore-cart-items-service
docker rmi "$name"
docker build . -t "$name"