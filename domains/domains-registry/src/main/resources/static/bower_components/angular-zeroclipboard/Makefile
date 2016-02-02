NODE ?=

build:
	@$(NODE) ./node_modules/.bin/uglifyjs \
		src/angular-zeroclipboard.static -o dist/angular-zeroclipboard.min.static \
		--source-map dist/angular-zeroclipboard.min.map -c -m sort

.PHONY: build
