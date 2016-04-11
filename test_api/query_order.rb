#!/usr/local/bin/ruby

require 'net/http'
require 'digest'
require 'json'

t = Time.now.to_i

md5 = Digest::MD5.new
md5.update t.to_s + 'kikirace'
key = md5.hexdigest

uri = URI('http://kikistore.csmuse.com/kikistore/api/kikirace_queryOrder.php')
params = { :Username => 'B123070146', :Password => "b03902107", :Time => t, :Key => key}
uri.query = URI.encode_www_form(params)

puts uri

res = Net::HTTP.get(uri).force_encoding("UTF-8")
hash = JSON.parse(res.to_s)
puts hash
