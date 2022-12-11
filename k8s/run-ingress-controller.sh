#!/bin/sh

helm install nginx-ingress nginx-stable/nginx-ingress --set rbac.create=true