FROM registry.access.redhat.com/ubi9/nodejs-22:latest AS build
WORKDIR /opt/app-root/src
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

FROM registry.access.redhat.com/ubi9/nginx-124:latest
COPY --from=build /opt/app-root/src/dist .
EXPOSE 8080
CMD ["nginx", "-g", "daemon off;"]
