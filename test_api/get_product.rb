#!/usr/local/bin/ruby

require 'net/http'
require 'digest'
require 'json'

t = Time.now.to_i

md5 = Digest::MD5.new
md5.update t.to_s + 'kikirace'
key = md5.hexdigest

uri = URI('http://kikistore.csmuse.com/kikistore/api/kikirace_getProductDetail.php')
params = { :ProductSN => ARGV[0].to_i, :Language => ARGV[1].to_i, :Time => t, :Key => key}
uri.query = URI.encode_www_form(params)

res = Net::HTTP.get(uri).force_encoding("UTF-8")
hash = JSON.parse(res[2..-1].to_s)
puts hash
