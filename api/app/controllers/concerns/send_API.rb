module Send_API

  extend ActiveSupport::Concern

  require 'net/http'
  require 'digest'

  def get_product(id)
    set_time_key

    uri = URI('http://kikistore.csmuse.com/kikistore/api/kikirace_getProductDetail.php')
    params = { :ProductSN => id, :Language => 2, :Time => @unix_time, :Key => @key }
    uri.query = URI.encode_www_form(params)

    res = Net::HTTP.get(uri).force_encoding("UTF-8")
    hash = JSON.parse(res[2..-1].to_s)

    hash['Product'][0]['SellPriceCNY'] = hash['Product'][0]['SellPriceCNY'].ceil.to_i

    photos = ['LargeIcon', 'SmallIcon']
    (1..8).each { |num| photos << 'ProductPhoto' + num.to_s }
    photos.each do |photo|
      hash['Product'][0][photo] = 'none' if hash['Product'][0][photo] == 'http://kikistore.csmuse.com/kikistore/upload/product/'
    end

    hash
  end

  def create_order(data)
    set_time_key

    uri = URI('http://kikistore.csmuse.com/kikistore/api/kikirace_createOrder.php')
    params = { :Username => 'B123070146', :Password => 'b03902107', :Time => @unix_time, :Key => @key }.merge(data)
    uri.query = URI.encode_www_form(params)

    res = Net::HTTP.get(uri).force_encoding("UTF-8")
    hash = JSON.parse(res[2..-1].to_s)
  end

  def query_order
    set_time_key

    uri = URI('http://kikistore.csmuse.com/kikistore/api/kikirace_queryOrder.php')
    params = { :Username => 'B123070146', :Password => 'b03902107', :Time => @unix_time, :Key => @key }
    uri.query = URI.encode_www_form(params)

    res = Net::HTTP.get(uri).force_encoding("UTF-8")
    hash = JSON.parse(res.to_s)
  end

  def query_order_by_orderNo(id)
    hash = query_order

    hash['Order'].select! { |order|  order['ExternalOrderNo'] == id }

    hash
  end

  def set_time_key
    @unix_time = Time.now.to_i

    md5 = Digest::MD5.new
    md5.update @unix_time.to_s + 'kikirace'
    @key = md5.hexdigest
  end

end
